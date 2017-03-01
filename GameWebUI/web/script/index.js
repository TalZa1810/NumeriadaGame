/**
 * Created by talza on 01/03/2017.
 */


function checkIfWritten() {
    var userName = document.getElementById('username').value;
    if (userName == null || userName == "") {
        document.getElementById('submitButton').disabled = true;
    } else {
        document.getElementById('submitButton').disabled = false;
    }
}


//AJAX
function checkIfNameExists() {
    var userName = $("#username").val();
    var isPlayerHuman = ($('#radioButtonHuman').is(':checked'));

    $.ajax({
        url: "nameChecker",
        data: ({ name : userName, isHuman : isPlayerHuman }),
        dataType: 'json',
        success: function(data) {
            console.log("Succeeded getting a result for name checking");
            if(data.playerAdded === true) {
                window.location.href = '../Griddler_Game/lobby.html';
            } else if(data.playerAdded === false) {
                $("div.login-error").html('');
                $("div.login-error").append("The username has already been taken!<br>Please choose another username");
            }
        },
        error: function() {
            console.log("Failed getting a result for name checking");
        }
    });
}

$(window).on('load', function() {
    $("#loginForm").on("submit", function (event) {
        event.preventDefault();
        // actual logic, e.g. validate the form
        checkIfNameExists();
    });
} );