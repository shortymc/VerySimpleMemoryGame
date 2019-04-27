


var itemsArray = new Array();
var item = {};
var FIELDS_NUMBER = 16;
var randArray = new Array();
var result = {};


function memoryStart() {
    newGame(false);

}



function showTable(clickable) {

   $("#current_moves").text(result.curentMoves);
    $("#current_time").text(result.currentTime);
    
    for (var i = 0; i < FIELDS_NUMBER; i++) {
        var itm = document.getElementById('item_' + i);
        var img = document.getElementById('img_' + i);

        item = itemsArray[i];
        if (item.solved || item.open) {
            $("#item_" + i).html(item.value);
            if (item.solved) {
                $("#item_" + i).css('background-color', 'red');
            }

        } else {
            $("#img_" + i).attr("src", "andraganoid.jpg");
        }
    }

    if (clickable) {
        $("#new_game_btn").hide();
        $(".table_item").on("click", function () {

            var tid = this.id;
            tid = tid.substring(5);
            tid = tid.replace("_", "");
            if (!itemsArray[tid].open || !itemsArray[tid].solves)
            {
                $(".table_item").off("click");
                itemClicked(tid);
            }
        });
    }
}

function itemClicked(id) {
    itemsArray[id].open = true;

    showTable(true);


}

function newGame(clickable) {
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
    }
    result = {};
    result.curentMoves = 0;
    result.currentTime = 0;
    result.bestMoves = localStorage.bestMoves;
    result.bestTime = localStorage.bestTime;
    
    alert(result.bestMoves)
alert(result.bestTime)
  $("#best_moves").text(result.bestMoves);
   $("#best_time").text(result.bestTime);

    showTable(clickable);
}