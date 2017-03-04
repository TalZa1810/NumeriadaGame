
function getShowBoard(table){

    var actionType = "getShowBoard";
    var gamettl = $("#gameTable").find("tr.success").attr('value');

    $.ajax({
        url: "gamingRoom",
        data: {
            "gameTitle": gamettl,
            "ActionType": actionType
        },
        success: function (board) {
            createBoard(board, table);
        },
        error: function (data) {
            console.log(data);
        }
    });
}
function getBoard(table) {
    var actionType = "getBoard";

    $.ajax({
        url: "gamingRoom",
        data: {
            "ActionType": actionType
        },
        success: function (board) {
            createBoard(board, table);
        },
        error: function (data) {
            console.log(data);
        }
    });
}

function createBoard(theBoard,table) {
    console.log("board create");
    table.addClass("myTable");
    createBoardButtons(theBoard, table);
    //createDownPart(theBoard, table);
}

function createBoardButtons(board, table) {
    var columns = board.m_BoardSize;
    var rows = board.m_BoardSize;

    //private SquareData[][] m_Board;



    for (var row = 0; row < rows; row++) {

        //A <tr> element contains one or more <th> or <td> elements.
        var tr = document.createElement('tr');
        for (var column = 0; column < columns; column++) {

            //Standard cells - contains data (created with the <td> element)
            var td = document.createElement('td');
            setRowCol(td, row, column);

            var btn = document.createElement("button");
            setRowCol(btn, row, column);
            $(btn).addClass( "boardBtn");

            var boardBtn = board.m_Board[row][column];

            setButtonColor(boardBtn , btn);
            setButtonSymbol( boardBtn , btn );

            td.appendChild(btn);
            tr.appendChild(td);
        }
        table.append(tr);
    }
}

function setRowCol(elm, row, col){
    elm.setAttribute("column",col);
    elm.setAttribute("row",row);
}

function setButtonColor(buttonObject, btn){

    switch(buttonObject.m_Color) {
        case "BLACK":
            $(btn).addClass( "blackBtn");
            break;
        case "BLUE":
            $(btn).addClass( "blueBtn");
            break;
        case "RED":
            $(btn).addClass( "redBtn");
            break;
        case "GREEN":
            $(btn).addClass( "greenBtn");
            break;
        case "MAGENTA":
            $(btn).addClass( "mangentaBtn");
            break;
        case "ORANGE":
            $(btn).addClass( "orangeBtn");
            break;
        case "PURPLE":
            $(btn).addClass( "purpleBtn");
            break;
        default:
            $(btn).addClass( "greyBtn");
    }
}

function setButtonSymbol(buttonObject, btn){

    btn.innerText = buttonObject.m_SquareSymbol;

}


/*
function createDownPart(board, table) {
    var theBoard = board.board;
    var rowsBlocks = board.rowsBlocks;
    var columnsBlocks = board.columnsBlocks;
    var rows = theBoard.length;
    var columns = theBoard[0].length;
    var maxRowBlock = getMaxLengthOfList(rowsBlocks);

    for (var row = 0; row < rows; row++) {
        var tr = document.createElement('tr');
        for (var column = 0; column < columns + maxRowBlock; column++) {
            var td = document.createElement('td');
            td.setAttribute("row", row);
            if (column < maxRowBlock && maxRowBlock - column <= rowsBlocks[row].length) {
                td.classList.add("block");
                var index = [rowsBlocks[row].length - maxRowBlock + column];
                td.setAttribute("blockIndex",index);
                td.innerText = rowsBlocks[row][index].m_Value;
            }
            else if (column >= maxRowBlock) {   // add class according to real board
                td.setAttribute("column", (column - maxRowBlock));
                td.classList.add("toggler");
                td.classList.add(theBoard[row][column - maxRowBlock].toLowerCase());
            }
            else {
                td.classList.add("block");
            }
            tr.appendChild(td);
        }

        table.append(tr);
    }
}

*/

/*

function getMaxLengthOfList(list) {
    var retValue = 0;
    for (var i = 0; i < list.length; i++) {
        if (list[i].length > retValue) {
            retValue = list[i].length;
        }
    }
    return retValue;
}*/