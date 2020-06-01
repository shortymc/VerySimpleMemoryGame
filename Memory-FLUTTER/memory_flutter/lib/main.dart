import 'dart:async';

import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:memory_flutter/memory_game.dart';
import 'package:shared_preferences/shared_preferences.dart';

const int FIELDS_NUMBER = 16;
const int FIELD_ROWS = 4;
const int FIELD_COLS = 4;
List<Field> fieldsList;
bool clickable = false;
bool btnVisibility = true;
Timer stopwatch;

SharedPreferences prefs;
var result;
var open;

void main() => _getPrefs();

_getPrefs() async {
  WidgetsFlutterBinding.ensureInitialized();
  await SharedPreferences.getInstance().then((SharedPreferences sp) {
    prefs = sp;
    runApp(MemoryMain());
  });
}

class MemoryMain extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.green,
      ),
      home: MemoryState(),
    );
  }
}

class MemoryState extends StatefulWidget {
  @override
  _MemoryState createState() => _MemoryState();
}

class _MemoryState extends State<MemoryState> with WidgetsBindingObserver {
  _MemoryState createState() => _MemoryState();

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
    _newGame();
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    super.dispose();
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    switch (state) {
      case AppLifecycleState.inactive:
      case AppLifecycleState.paused:
        if (stopwatch != null) {
          stopwatch.cancel();
        }
        break;
      case AppLifecycleState.resumed:
        _newGame();
        clickable = false;
        btnVisibility = true;
        setState(() {});
        break;
      case AppLifecycleState.detached:
        break;
    }
  }

  void _newGame() {
    if (stopwatch != null) {
      stopwatch.cancel();
    }
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

  _stopwatch() {
    var start = DateTime.now().millisecondsSinceEpoch;
    Timer.periodic(
      Duration(milliseconds: 1000),
      (timer) {
        stopwatch = timer;
        result.currentTime = DateTime.now().millisecondsSinceEpoch - start;
        setState(() {});
      },
    );
  }

  @override
  Scaffold build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Memory-FLUTTER"),
      ),
      body: Padding(
        padding: EdgeInsets.symmetric(horizontal: 6),
        child: Column(
          children: <Widget>[
            _setRow("Best moves: ", result.bestMoves),
            _setRow("Best time: ", result.bestTime ~/ 1000),
            _setTable(),
            _setRow("Current moves: ", result.currentMoves),
            _setRow("Current time: ", result.currentTime ~/ 1000),
            _setButton()
          ],
        ),
      ),
    );
  }

  _newGameBtn() {
    clickable = true;
    btnVisibility = false;
    _newGame();
    _stopwatch();
  }

  Visibility _setButton() {
    return Visibility(
      visible: btnVisibility,
      child: new SizedBox(
        width: double.infinity,
        child: MaterialButton(
          onPressed: _newGameBtn,
          color: Colors.green,
          child: Text("New game"),
        ),
      ),
    );
  }

  Row _setRow(txt, val) {
    return Row(
      children: <Widget>[_setText(txt), _setText(val.toString())],
    );
  }

  Padding _setText(txt) {
    return Padding(
      padding: EdgeInsets.symmetric(vertical: 6),
      child: Text(
        txt,
        style: TextStyle(fontSize: 20),
      ),
    );
  }

  Expanded _setTable() {
    return Expanded(
      child: Column(
        children: _getItemRows(),
      ),
    );
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

  Expanded _getItem(indx) {
    var itm = fieldsList[indx];
    return Expanded(
      child: GestureDetector(
        onTap: () {
          _itemClicked(indx);
        },
        child: Container(
          margin: EdgeInsets.all(2),
          decoration: BoxDecoration(
              border: Border.all(width: 3),
              borderRadius: BorderRadius.all(Radius.circular(16))),
          height: MediaQuery.of(context).size.height * 0.12,
          child: IndexedStack(
            index: itm.open || itm.solved ? 1 : 0,
            //    index: 1,
            children: <Widget>[
              Align(
                child: Image.asset(
                  'assets/andraganoid.jpg',
                  alignment: Alignment.center,
                ),
              ),
              Align(
                child: Text(
                  itm.item.toString(),
                  style: TextStyle(fontSize: 40),
                ),
                alignment: Alignment.center,
              ),
            ],
          ),
        ),
      ),
    );
  }

  void _itemClicked(indx) {
    if (clickable) {
      if (fieldsList[indx].open != true && fieldsList[indx].solved != true) {
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
            Timer(
              Duration(seconds: 1),
              () {
                fieldsList[f].open = false;
                fieldsList[s].open = false;
                clickable = true;
                setState(() {});
              },
            );
          }
          open.first = -1;
          open.second = -1;
          setState(() {});
        }
      }
    }
  }

  void _endGame() {
    stopwatch.cancel();
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
