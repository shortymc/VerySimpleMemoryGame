import 'package:memory_flutter/main.dart';


class MemoryGame {}

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

class Result {
  int currentMoves;
  int bestMoves;
  num currentTime;
  num bestTime;

  Result() {
    this.bestMoves = prefs.getInt("bestMoves") ?? 100;
    this.bestTime = prefs.get("bestTime") ?? 1000 * 1000;
    this.currentMoves = 0;
    this.currentTime = 0;
  }
}

class Open {
  int first;
  int second;
 // int opened;
  int solved;

  Open() {
    this.first = -1;
    this.second = -1;
  // this.opened = 0;
    this.solved = 0;
  }



}
