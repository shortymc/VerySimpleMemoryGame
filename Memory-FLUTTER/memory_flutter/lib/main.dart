import 'package:flutter/material.dart';

List<Field> fieldsList;
var bMoves;
var bTime;
var cMoves;
var cTime;
final int FIELDS_NUMBER = 16;
final int FIELD_ROWS = 4;
final int FIELD_COLS = 4;

void main() {
  runApp(MemoryMain());
}

class MemoryMain extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Memory-FLUTTER",
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.green,
      ),
      home: Memory(),
    );
  }
}

class Memory extends StatefulWidget {
  Memory({Key key}) : super(key: key);

  @override
  _MemoryState createState() => _MemoryState();
}

class _MemoryState extends State<Memory> {
  _MemoryState() {
    _newGame();
  }

  void _newGame() {
    fieldsList = List(FIELDS_NUMBER);
    for (var i = 0; i < FIELDS_NUMBER / 2; i++) {
      fieldsList[i] = (Field(i));
      fieldsList[i + (FIELDS_NUMBER / 2).toInt()] = (Field(i));
      print(i);
    }
    fieldsList.shuffle();
    fieldsList.shuffle();
    fieldsList.shuffle();

    bMoves = 100;
    bTime = 1000 * 1000;
    cMoves = 0;
    cTime = 0;
  }

//// Default placeholder text
//  String textToShow = "I Like Flutter";
//
//  void _updateText() {
//    setState(() {
//      // update the text
//      textToShow = "Flutter is Awesome!";
//    });
//  }
//

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("Memory-FLUTTER"),
        ),
        body: Column(children: <Widget>[
          _setRow("Best moves: ", bMoves),
          _setRow("Best time: ", bTime ~/ 1000),
          _setTable(),
          _setRow("Current moves: ", cMoves),
          _setRow("Currentt time: ", cTime ~/ 1000),
          MaterialButton(
            onPressed: _newGame,
            color: Colors.green,
            child: Text("New game"),
          )
        ]));
  }

  Widget _setRow(txt, val) {
    return Row(
      children: <Widget>[_setText(txt), _setText(val.toString())],
    );
  }

  Widget _setText(txt) {
    return Padding(
        padding: EdgeInsets.all(6),
        child: Text(
          txt,
          style: TextStyle(fontSize: 20),
        ));
  }

  Widget _setTable() {
    return Expanded(
        child: Column(
      children: _getItemRows(),
    ));
  }

  List<Widget> _getItemRows() {
    List<Widget> itemRows = List(FIELD_ROWS);

    for (var i = 0; i < FIELD_ROWS; i++) {
      itemRows[i] = Row(children: _getItemCols(i));
    }
    return itemRows;
  }


//  child: IntrinsicHeight(
//  child: Row(crossAxisAlignment: CrossAxisAlignment.stretch, children: [
//  Expanded(

  List<Widget> _getItemCols(row) {
    List<Widget> itemCols = List(FIELD_COLS);

    for (var j = 0; j < FIELD_COLS; j++) {
      itemCols[j] = _getItem(row * FIELD_ROWS + j);
    }
    return itemCols;
  }

  Widget _getItem(indx) {
//    return Text(
//            indx.toString(),
//            style: Theme.of(context).textTheme.headline,
//          );

    var itm = fieldsList[indx];
    return Expanded(
        child: Container(
      child: IndexedStack(
        index: itm.open ? 1 : 0,
        //    index: 1,
        children: <Widget>[
          Image.asset(
            'assets/andraganoid.jpg',
          ),
          Text(
            itm.item.toString(),
            style: Theme.of(context).textTheme.headline,
          ),
        ],
      ),
    ));
  }
}

class Field {
  int item;
  bool open;
  bool solved;

  Field(int item) {
    this.item = item;
    this.open = false;
    this.solved = false;
  }
}
