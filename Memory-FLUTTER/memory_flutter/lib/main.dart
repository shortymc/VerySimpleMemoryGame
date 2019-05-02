import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:memory_flutter/memory_game.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'dart:async';

final int FIELDS_NUMBER = 16;
final int FIELD_ROWS = 4;
final int FIELD_COLS = 4;
List<Field> fieldsList;
bool clickable = false;
bool btnVisibility = true;

SharedPreferences prefs;
var result;
var open;

void main() {
  _getPrefs();
}

_getPrefs() async {
  await SharedPreferences.getInstance().then((SharedPreferences sp) {
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

  void _newGame() {
    fieldsList = List(FIELDS_NUMBER);
    for (var i = 0; i < FIELDS_NUMBER / 2; i++) {
      fieldsList[i] = (Field(i));
      fieldsList[i + FIELDS_NUMBER ~/ 2] = (Field(i));
    }
    fieldsList.shuffle();
    fieldsList.shuffle();
    fieldsList.shuffle();
    result = Result();
    open = Open();

    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("Memory-FLUTTER"),
        ),
        body: Padding(
            padding: EdgeInsets.symmetric(horizontal: 6),
            child: Column(children: <Widget>[
              _setRow("Best moves: ", result.bestMoves),
              _setRow("Best time: ", result.bestTime ~/ 1000),
              _setTable(),
              _setRow("Current moves: ", result.currentMoves),
              _setRow("Current time: ", result.currentTime ~/ 1000),
              _setButton()
            ])));
  }

  _newGameBtn() {
    clickable = true;
    btnVisibility = false;
    _newGame();
  }

  Widget _setButton() {
    return Visibility(
        visible: btnVisibility,
        child: new SizedBox(
            width: double.infinity,
            child: MaterialButton(
              onPressed: _newGameBtn,
              color: Colors.green,
              child: Text("New game"),
            )));
  }

  Widget _setRow(txt, val) {
    return Row(
      children: <Widget>[_setText(txt), _setText(val.toString())],
    );
  }

  Widget _setText(txt) {
    return Padding(
        padding: EdgeInsets.symmetric(vertical: 6),
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
    if (clickable) {
      fieldsList[indx].open = true;

      if (open.first == -1) {
        open.first = indx;
        open.second = -1;
        setState(() {});
      } else {
        open.second = indx;
        result.currentMoves++;
        if (fieldsList[open.first].item == fieldsList[open.second].item) {
          fieldsList[open.first].solved = true;
          fieldsList[open.second].solved = true;
          open.solved++;

          if (open.solved == FIELDS_NUMBER / 2) {
            _endGame();
          }
        } else {
          clickable = false;
          var f = open.first;
          var s = open.second;
          Timer(Duration(seconds: 1), () {
            fieldsList[f].open = false;
            fieldsList[s].open = false;
            clickable = true;
            setState(() {});
          });
        }
        open.first = -1;
        open.second = -1;
        setState(() {});
      }
    }
  }

  void _endGame() {
    Fluttertoast.showToast(
        msg: "Game Over",
        toastLength: Toast.LENGTH_LONG,
        gravity: ToastGravity.CENTER,
        backgroundColor: Colors.red,
        textColor: Colors.white,
        fontSize: 16.0);

    if (result.currentMoves < result.bestMoves) {
      result.bestMoves = result.currentMoves;
      prefs.setInt("bestMoves", result.currentMoves);

      Fluttertoast.showToast(
          msg: "Best moves",
          toastLength: Toast.LENGTH_LONG,
          gravity: ToastGravity.CENTER,
          backgroundColor: Colors.blue,
          textColor: Colors.white,
          fontSize: 16.0);
    }

    if (result.currentTime < result.bestTime) {
      result.bestTime = result.currentTime;
      prefs.setInt("bestTime", result.currentTime);

      Fluttertoast.showToast(
          msg: "Best Time",
          toastLength: Toast.LENGTH_LONG,
          gravity: ToastGravity.CENTER,
          backgroundColor: Colors.green,
          textColor: Colors.white,
          fontSize: 16.0);
    }
    btnVisibility = true;
    setState(() {});
  }
}
