public class PathFinder {
  private final GridWorld gridWorld;
  private Move[][] previousMoveData;

  public PathFinder(GridWorld gridWorld) {
    this.gridWorld = gridWorld;
    this.previousMoveData = new Move[gridWorld.getRowCount()][gridWorld.getColCount()];
  }

  // Return a list of moves to get from the starting point of the grid to the
  // ending
  public SinglyLinkedList<Move> findShortestPath() {
    // Implement this!
    SinglyLinkedList<Move> moves = new SinglyLinkedList<>();
    GridIndex goal = gridWorld.getEndingPoint();
    int goalrow = goal.row;
    int goalcol = goal.col;
    LinkedQueue<GridIndex> queue = new LinkedQueue<GridIndex>();
    queue.enqueue(gridWorld.getStartingPoint());
    while (!queue.isEmpty()) {
      GridIndex index = queue.dequeue();
      if (gridWorld.tryMove(index, Move.UP) != null && !gridWorld.isBlocked(gridWorld.tryMove(index, Move.UP))) {
        GridIndex next = gridWorld.tryMove(index, Move.UP);
        if (previousMoveData[next.row][next.col] == null) {
          previousMoveData[next.row][next.col] = Move.UP;
          queue.enqueue(next);
        }
      }
      if (gridWorld.tryMove(index, Move.DOWN) != null && !gridWorld.isBlocked(gridWorld.tryMove(index, Move.DOWN))) {
        GridIndex next = gridWorld.tryMove(index, Move.DOWN);
        if (previousMoveData[next.row][next.col] == null) {
          previousMoveData[next.row][next.col] = Move.DOWN;
          queue.enqueue(next);
        }
      }
      if (gridWorld.tryMove(index, Move.LEFT) != null && !gridWorld.isBlocked(gridWorld.tryMove(index, Move.LEFT))) {
        GridIndex next = gridWorld.tryMove(index, Move.LEFT);
        if (previousMoveData[next.row][next.col] == null) {
          previousMoveData[next.row][next.col] = Move.LEFT;
          queue.enqueue(next);
        }
      }
      if (gridWorld.tryMove(index, Move.RIGHT) != null && !gridWorld.isBlocked(gridWorld.tryMove(index, Move.RIGHT))) {
        GridIndex next = gridWorld.tryMove(index, Move.RIGHT);
        if (previousMoveData[next.row][next.col] == null) {
          previousMoveData[next.row][next.col] = Move.RIGHT;
          queue.enqueue(next);
        }
      }
      if (previousMoveData[goalrow][goalcol] != null)
        break;
    }
    if (previousMoveData[goalrow][goalcol] == null) {
      return null;
    } else {
      GridIndex index = gridWorld.getEndingPoint();
      while (!gridWorld.isStartingPoint(index)) {
        moves.addFirst(previousMoveData[index.row][index.col]);
        index = gridWorld.tryUndoMove(index, previousMoveData[index.row][index.col]);
      }
    }
    return moves;
  }
}