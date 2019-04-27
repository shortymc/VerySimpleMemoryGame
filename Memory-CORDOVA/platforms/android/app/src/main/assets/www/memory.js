


var itemsArray = new Array();
var item = {};
var FIELDS_NUMBER = 16;
//var randItem = [0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7];
var randArray = new Array();
function memoryStart() {
    newGame(false);

}
function newGame(clickable) {
    itemsArray = [];
//    randItem.sort(() => Math.random() - 0.5);
//    randItem.sort(() => Math.random() - 0.5);
//    randItem.sort(() => Math.random() - 0.5);
    randArray = [];
    for (var i = 0; i < FIELDS_NUMBER / 2; i++) {
        randArray.push(i);
        randArray.push(i);
    }
 randArray.random();

    for (var i = 0; i < FIELDS_NUMBER; i++) {
        item = {};
        item.value = randArray[i];
        item.index = i;
        item.open = true;
        item.solved = false;
        itemsArray.push(item);
    }
    showTable(clickable);
}


function showTable(clickable) {
    for (var i = 0; i < FIELDS_NUMBER; i++) {
        var itm = document.getElementById('item_' + i);
        var img = document.getElementById('img_' + i);
        img.src = "andraganoid.jpg";
        item = itemsArray[i];
        if (item.solved || item.open) {
            img.style.visibility = "hidden";
            itm.innerHTML = item.value;

        } else {
            img.style.visibility = "visible";
            itm.innerHTML = "";
        }
    }
}