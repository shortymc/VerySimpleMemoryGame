import 'package:memory_flutter/main.dart';
import 'package:shared_preferences/shared_preferences.dart';


class MemoryGame {

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

class Result {
  int currentMoves;
  int bestMoves;
  num currentTime;
  num bestTime;
  int solved;

  Result(prefs) {
  //  if (prefs != null) {
      this.bestMoves = prefs.get("bestMoves") ?? 100;
      this.bestTime = prefs.get("bestTime") ?? 1000 * 1000;
//    } else {
//      this.bestMoves = 100;
//      this.bestTime = 1000 * 1000;
//    }
    this.currentMoves = 0;
    this.currentTime = 0;
    this.solved = 0;
    print("1111 $bestTime");
  }

}
