import 'package:flutter/material.dart';

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
  var bMoves = 100;
  var bTime = 1000 * 1000;
  var cMoves = 0;
  var cTime = 0;
  final int FIELDS_NUMBER = 16;
  final int FIELD_ROWS = 4;
  final int FIELD_COLS = 4;

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
          Row(
            children: <Widget>[Text("Best moves: "), Text(bMoves.toString())],
          ),
          Row(
            children: <Widget>[Text("Best time: "), Text(bTime.toString())],
          ),
          Expanded(
            child: GridView.count(
              crossAxisCount: FIELD_ROWS,
              children: List.generate(FIELDS_NUMBER, (index) {
                return IndexedStack(index: 1, children: <Widget>[
                  Text(
                    index.toString(),
                    style: Theme.of(context).textTheme.headline,
                  ),
                  Image.asset(
                    'assets/andraganoid.jpg',
                    fit: BoxFit.fill,
                  )
                ]);
              }),
            ),
          ),
          Row(
            children: <Widget>[
              Text("Current moves: "),
              Text(cMoves.toString())
            ],
          ),
          Row(
            children: <Widget>[Text("Current time: "), Text(cTime.toString())],
          ),
        ]));
  }
}
