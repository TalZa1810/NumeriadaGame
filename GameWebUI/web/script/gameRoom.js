var refreshRate = 1000; //miliseconds

$(document).ready(function () {
    $.ajaxSetup({cache: false});

    $('#GameAction').hide();
    $('#Replay').hide();
    $('#visitorsTable').hide();

    $('#buttonQuit').on("click", ajaxQuitGame);
    $('.boardBtn').on("click", ajaxBoardBtnClicked);


    ajaxGamesDeatilsAndPlayers();

    GamesDeatilsAndPlayers = setInterval(ajaxGamesDeatilsAndPlayers, refreshRate);


    getBoard($('#board'));

    realPlayer();
    /*
    var actionType = "isVisitor";
    $.ajax({
        url: "gamingRoom",
        data: {
            "ActionType": actionType
        },
        success:function (result){
            if(!result){
               realPlayer();
            }
        }
    });
    */

});

function ajaxBoardBtnClicked(btnClicked) {

    var actionType = "doMove";
    $.ajax({
        url: "gamingRoom",
        data: {
            "row": btnClicked.currentTarget.getAttribute("row"),
            "column": btnClicked.currentTarget.getAttribute("column"),
            "ActionType": actionType
        },
        success:function (gameData){
            var gameInfo = gameData[0];
            var board = gameData[1];
            if(gameInfo.m_ErrorFound){
                openPopup(gameInfo.m_ErrorMsg)
            }
            else {
                $('#board').empty();
                createBoard(board, $('#board'));
            }
        }
    });

}

function visitoPlayer() {
    $('#buttonQuit').val("Back To Lobby");
    setInterval(ajaxVisitorBoard,refreshRate);

}

function ajaxVisitorBoard() {
    var actionType = "pullVisitorBoard";

    $.ajax({
        url: "gamingRoom",
        data: {
            "ActionType": actionType
        },
        success: function (boardInfo) {
            updateAllBoard(boardInfo);
        }
    });
}

function realPlayer() {
    $("#radioMark").prop("checked", true);

    startGame = setInterval(ajaxIsGameStarted, refreshRate);

    $('#GameInfo').hide();

    $('.option').on("click", actionSelected);
}

function startIfFirstPlayerComputer() {

    var actionType = "firstPlyComputer";

    $.ajax({
        url: "gamingRoom",
        data: {
            "ActionType": actionType
        },
        success:function (isComp){
            if(isComp === "true"){
                setInterval(ajaxBoard,refreshRate); //onli if is computer it will pull every min
                $('#GameAction').hide();
            }
            else
            {
                $('#GameAction').fadeIn(200);
            }
        }
    });
}

function ajaxBoard() {

    var actionType = "pullBoard";

    $.ajax({
        url: "gamingRoom",
        data: {
            "ActionType": actionType
        },
        success: function (boardInfo) {
            updateAllBoard(boardInfo);
        }
    });
}

function ajaxGamesDeatilsAndPlayers() {
    var actionType = "GameStatus";

    $.ajax({
        url: "gamingRoom",
        data: {
            "ActionType": actionType
        },
        success: function (data) {
            var players = data[0];
            var gameDetails = data[1];
            var PlayerFromSesion = data[2];

            refreshGameDeatils(gameDetails,PlayerFromSesion );
            refreshPlayerList(players, PlayerFromSesion);
        }
    });
}

function refreshPlayerList(players, PlayerFromSesion) {
    $("#playingUsersTable").empty();
    $.each(players || [], function (index, user) {
        var icon;
        if (user.m_PlayerType === 'Human') {
            icon = "<span class='HumanIcon'></span>"
        }
        else {
            icon = "<span class='MachineIcon'></span>"
        }

        var userList = $('<tr> </tr>');
        $('<th>' + user.m_Name + '</th>').appendTo(userList);
        $('<th>' + icon + '</th>').appendTo(userList);
        $('<th>' + user.m_Color + '</th>').appendTo(userList);
        $('<th>' + user.m_Score + '</th>').appendTo(userList);
        userList.appendTo($("#playingUsersTable"));

        if (PlayerFromSesion == user.userName) {
            userList.addClass('success');
        }
    });

}

function refreshVisitors(visitors) {

    if(visitors.length == 0) {
        $('#visitorsTable').hide();
    } else {
        $('#visitorsTable').show();
        $('#allVisitors').empty();
        for (var i = 0; i < visitors.length; i++) {
            var visitor = $('<tr> </tr>');
            $('<th>' + (i + 1) + '</th>').appendTo(visitor);
            $('<th>' + visitors[i] + '</th>').appendTo(visitor);
            visitor.appendTo($("#allVisitors"));
        }
    }
}

function refreshGameDeatils(gameDetails, PlayerFromSesion) {

    $('#loggedinUser').text("Welcome " + PlayerFromSesion);
    $('#lableGameTitle').text(gameDetails.m_GameTitle);
    $('#lableCurrentPlayer').text(gameDetails.m_CurrentPlayer.m_Name);
    $('#lableCurrentMove').text( gameDetails.m_NumOfMoves);


    if  (gameDetails.winnerName !== undefined || gameDetails.finishAllRound === true){
        if (gameDetails.technicalVictory === true) {
            openPopup("Technical Victory To " + gameDetails.winnerName + "!!!");
        } else {
            if(gameDetails.winnerName === undefined)
            {
                openPopup("We Have No Winner")
            }
            else
            openPopup(gameDetails.winnerName + " is Win!!!");
        }
        $('#GameAction').hide();
        $('#GameInfo').hide();
        $('#buttonQuit').val("Back To Lobby");
        clearInterval(GamesDeatilsAndPlayers);
    }
}

function ajaxQuitGame() {
    var actionType = "ExitGame";

    $.ajax({
        url: "gamingRoom",
        data: {
            "ActionType": actionType
        },
        success: function (data) {
            window.location.replace("Lobby.html");
        },
        error: function (data) {
            console.log(data);
        }
    });
}

function ajaxIsGameStarted() {
    var actionType = "isGameStarted";

    $.ajax({
        url: "gamingRoom",
        data: {
            "ActionType": actionType
        },
        success: function (isGameStarted) {
            if (isGameStarted === true) {
                openPopup("Game Started!");

                clearInterval(startGame);
                $('#GameInfo').fadeIn(200);
                startIfFirstPlayerComputer();
            }
        },
    });
}

function replayExit() {

     ajaxBoard();
     $('#Replay').hide();
     $('#GameAction').fadeIn(200);
 }

function nextMove() {
    ajaxNextOrPrev("true");
}

function prevMove(){
    ajaxNextOrPrev("false");
}

function ajaxNextOrPrev(next){
    var actionType = "prevOrNext";

    $.ajax({
        url: "gamingRoom",
        data: {
            "ActionType": actionType,
            "requestType": next
        },
        success: function (result) {
            if (result[0]) {    // existBoard
                updateAllBoard(result[1]);
            } else {
                openPopup(result[1]);
            }
            removeSelectedSquares();
        }
    });
}

function replyFromEnd(){
ajaxStartReplay("false");
}

function replayFromStart() {
    ajaxStartReplay("true");
}

function ajaxStartReplay(start){
    var actionType = "replay";
    $.ajax({
        url: "gamingRoom",
        data: {
            "ActionType": actionType,
            "requestType": start
        },
        success: function (result) {
            if (result[0]) {    // existBoard
                $('#GameAction').hide();
                $('#Replay').fadeIn(200);
                updateAllBoard(result[1]);
            } else {
                openPopup(result[1]);
            }
            removeSelectedSquares();
        },
        error:function (e) {
            console.log(e);
        }
    });
}

function actionSelected() {
    $('.option').removeClass('optionSelected');
    $(this).addClass('optionSelected');
}

function openPopup(msg) {
    $("#message").html(msg);
    $("#popup").show();
}

function closePopup() {
    $("#popup").hide();
}