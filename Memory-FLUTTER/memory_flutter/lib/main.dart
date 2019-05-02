import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:memory_flutter/MemoryGame.dart';

final int FIELDS_NUMBER = 16;
final int FIELD_ROWS = 4;
final int FIELD_COLS = 4;
List<Field> fieldsList;

SharedPreferences prefs;
var mGame = MemoryGame();
var result;

void main() {
  _getPrefs();
//  runApp(MemoryMain());
}

_getPrefs() async {
  await SharedPreferences.getInstance().then((SharedPreferences sp) {
    print("START");
    prefs = sp;
    runApp(MemoryMain());
  });
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
  @override
  void initState() {
    super.initState();
    _newGame();
  }

//  _MemoryState() {
//    _newGame();
//  }

  void _newGame() {
    fieldsList = List(FIELDS_NUMBER);
    for (var i = 0; i < FIELDS_NUMBER / 2; i++) {
      fieldsList[i] = (Field(i));
      fieldsList[i + FIELDS_NUMBER ~/ 2] = (Field(i));
      print(i);
    }
    fieldsList.shuffle();
    fieldsList.shuffle();
    fieldsList.shuffle();
    result = Result(prefs);

    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("Memory-FLUTTER"),
        ),
        body: Column(children: <Widget>[
          _setRow("Best moves: ", result.bestMoves),
          _setRow("Best time: ", result.bestTime ~/ 1000),
          _setTable(),
          _setRow("Current moves: ", result.currentMoves),
          _setRow("Current time: ", result.currentTime ~/ 1000),
          MaterialButton(
            onPressed: _newGame,
            color: Colors.green,
            child: Text("New game"),
          )
        ]));
  }

  void _restartGame() {
    setState(() {});
    _newGame();
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
    var itm = fieldsList[indx];
    return Expanded(
        child: GestureDetector(
            onTap: () {
              _itemClicked(indx);
            },
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
            )));
  }

  void _itemClicked(indx) {
    print("CLICKED: $indx");
    fieldsList[indx].open = true;
    setState(() {});
    //  _updateItems();
  }
}
