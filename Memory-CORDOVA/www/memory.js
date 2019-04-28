
var itemsArray = new Array();
var item = {};
var FIELDS_NUMBER = 16;
var ALL_SOLVED = 8;
var randArray = new Array();
var result = {};
var stopwatch;
var start;


function showTable(clickable) {

    $("#current_moves").text(result.curentMoves);

    for (var i = 0; i < FIELDS_NUMBER; i++) {
        item = itemsArray[i];
        if (item.solved || item.open) {
            $("#img_" + i).hide();
            $("#txt_" + i).show();
            if (item.solved) {
                $("#item_" + i).css('background-color', 'red');
            }
        } else {
            $("#img_" + i).show();
            $("#txt_" + i).hide();
        }
    }

    if (clickable) {

        $(".table_item").on("click", function () {

            var tid = this.id;
            tid = tid.substring(5);
            tid = tid.replace("_", "");
            if (!itemsArray[tid].open && !itemsArray[tid].solved) {
                $(".table_item").off("click");
                itemsArray[tid].open = true;
                result.opened++;
                itemClicked(tid);
            }
        });
    }
}

function itemClicked(id) {

    switch (result.opened) {

        case 1:
            result.first = id;
            showTable(true);
            break;

        case 2:
            result.curentMoves++;
            result.second = id;
            result.opened = 0;
            showTable(false);

            itemsArray[result.first].open = false;
            itemsArray[result.second].open = false;

            if (itemsArray[result.first].value == itemsArray[result.second].value) {
                itemsArray[result.first].solved = true;
                itemsArray[result.second].solved = true;
                result.solved++;
                checkIfAllSolved();
            } else {
                setTimeout(function () {
                    result.first = -1;
                    result.second = -1;
                    showTable(true);
                }, 1000)
            }
            break;
    }
}


function checkIfAllSolved() {
    showTable(true);

    if (result.solved == ALL_SOLVED) {
        alert("Game Over");
        if (result.curentMoves < localStorage.bestMoves) {
            localStorage.bestMoves = result.curentMoves;
            alert("New Best Moves");
        }
        if (result.currentTime < localStorage.bestTime) {
            localStorage.bestTime = result.currentTime;
            alert("New Best Time");
        }
        $("#new_game_btn").show();
        clearInterval(stopwatch);
    }
}



function newGame() {
    $("#new_game_btn").hide();
    randArray = [];
    for (var i = 0; i < FIELDS_NUMBER / 2; i++) {
        randArray.push(i);
        randArray.push(i);
    }

    randArray.sort(function (a, b) {
        return 0.5 - Math.random();
    });
    randArray.sort(function (a, b) {
        return 0.5 - Math.random();
    });
    randArray.sort(function (a, b) {
        return 0.5 - Math.random();
    });

    itemsArray = [];

    for (var i = 0; i < FIELDS_NUMBER; i++) {
        item = {};
        item.value = randArray[i];
        item.index = i;
        item.open = false;
        item.solved = false;
        itemsArray.push(item);
        $("#txt_" + i).text(item.value);
        $("#item_" + i).css('background-color', 'white');
    }

    resetResult();
    gameTimer();
    showTable(true);
}

function gameTimer() {
    clearInterval(stopwatch);
    start = new Date().getTime();
    stopwatch = setInterval(function () {
        result.currentTime = new Date().getTime() - start;
        console.log(result.currentTime)
        $("#current_time").text(getSeconds(result.currentTime));
    }, 1000);

}

function resetResult() {
    result = {};
    result.curentMoves = 0;
    result.currentTime = 0;
    result.bestMoves = localStorage.bestMoves;
    result.bestTime = localStorage.bestTime;

    result.first = -1;
    result.second = -1;
    result.solved = 0;
    result.opened = 0;

    $("#best_moves").text(result.bestMoves);
    $("#best_time").text(getSeconds(result.bestTime));
}

function getSeconds(tim) {
    return  Math.floor(tim / 1000);
}

