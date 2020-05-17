import java.util.Random;

public class TrackPacman {
    private final Ghost ghost;

    boolean availableDirectionUp;
    boolean availableDirectionDown;
    boolean availableDirectionRight;
    boolean availableDirectionLeft;

    public TrackPacman(Ghost ghost) {
        this.ghost = ghost;
    }

    public void trackPacman() {
        int ghostNumber = ghost.ghostNumber;


        if (ghostNumber == 1) {
            decideDirection1();
        } else if (ghostNumber == 2) {
            decideDirection2();
        } else if (ghostNumber == 3) {
            decideDirection3(); // chyba nie
        } else if (ghostNumber == 4) {
            decideDirection4(); // ma blad
        }

    }

    private boolean samePath() {
        if (ghost.x == ghost.pacmanX || ghost.y == ghost.pacmanY) {
            decideDirection3();
            return true;
        }
        return false;
    }


    // red ghost
    public void decideDirection1() {
        if (System.nanoTime() - ghost.timeDecideDirection >= 0.75e9) {
            Random rand = new Random();
            if (!samePath()) {
                while (!ghost.canMoveThisDirection(ghost.direction)) {
                    int tempDirection = rand.nextInt(4);
                    if (tempDirection == 0)
                        ghost.direction = LivingEntity.Direction.RIGHT;
                    if (tempDirection == 1)
                        ghost.direction = LivingEntity.Direction.UP;
                    if (tempDirection == 2)
                        ghost.direction = LivingEntity.Direction.DOWN;
                    if (tempDirection == 3)
                        ghost.direction = LivingEntity.Direction.LEFT;
                }
            }
            //ghost.timeDecideDirection = System.nanoTime();
        }

    }

    // pink ghost
    public void decideDirection2() {
        if (System.nanoTime() - ghost.timeDecideDirection >= 0.75e9) {
            if (!samePath()) {
                if (!ghost.canMoveThisDirection(ghost.direction)) {
                    if (ghost.canMoveThisDirection(ghost.pacmanDirection)) {
                        ghost.direction = ghost.pacmanDirection;
                    } else if (ghost.canMoveThisDirection(ghost.pacmanDirectionFuture)) {
                        ghost.direction = ghost.pacmanDirectionFuture;
                    } else {
                        decideDirection1();
                    }
                }
            }
            // ghost.timeDecideDirection = System.nanoTime();
        }
    }

    // orange ghost
    public void decideDirection3() {
        if (System.nanoTime() - ghost.timeDecideDirection >= 0.75e9) {
            findAvailableDirection();
            boolean isLeft;
            boolean isUp;
            int xDistanceFromPacman = ghost.x - ghost.pacmanX;
            int yDistanceFromPacman = ghost.y - ghost.pacmanY;
            isLeft = xDistanceFromPacman > 0;
            isUp = yDistanceFromPacman > 0;
            if (donMoveToBase(xDistanceFromPacman)) {

            } else if ((xDistanceFromPacman * xDistanceFromPacman) > (yDistanceFromPacman * yDistanceFromPacman)) {
                if (isLeft) {
                    if (availableDirectionLeft) {
                        ghost.direction = LivingEntity.Direction.LEFT;
                    } else {
                        moveDownorUp(isUp);
                    }
                } else {
                    if (availableDirectionRight) {
                        ghost.direction = LivingEntity.Direction.RIGHT;
                    } else {
                        moveDownorUp(isUp);
                    }
                }
            } else {
                if (isUp) {
                    if (availableDirectionUp) {
                        ghost.direction = LivingEntity.Direction.UP;
                    } else {
                        moveLeftorRight(isLeft);
                    }
                } else {
                    if (availableDirectionDown) {
                        ghost.direction = LivingEntity.Direction.DOWN;
                    } else {
                        moveLeftorRight(isLeft);
                    }
                }

            }
            //   ghost.timeDecideDirection = System.nanoTime();
        }

    }

    private void moveLeftorRight(boolean isLeft) {
        if (isLeft && availableDirectionLeft) {
            ghost.direction = LivingEntity.Direction.LEFT;
        } else if (availableDirectionRight) {
            ghost.direction = LivingEntity.Direction.RIGHT;
        } else if (availableDirectionLeft) {
            ghost.direction = LivingEntity.Direction.LEFT;
        }
    }

    private void moveDownorUp(boolean isUp) {
        if (isUp && availableDirectionUp) {
            ghost.direction = LivingEntity.Direction.UP;
        } else if (availableDirectionDown) {
            ghost.direction = LivingEntity.Direction.DOWN;
        } else if (availableDirectionUp) {
            ghost.direction = LivingEntity.Direction.UP;
        }
    }

    // yellow ghost
    public void decideDirection4() {
        if (System.nanoTime() - ghost.timeDecideDirection >= 0.75e9) {
            findAvailableDirection();
            int xDistanceFromPacman = ghost.x - ghost.pacmanX;
            int yDistanceFromPacman = ghost.y - ghost.pacmanY;

            if (!donMoveToBase(xDistanceFromPacman)) {
                if (!ghost.canMoveThisDirection(ghost.direction)) {
                    if ((xDistanceFromPacman * xDistanceFromPacman) > (yDistanceFromPacman * yDistanceFromPacman)) {
                        if (xDistanceFromPacman > 0 && availableDirectionLeft) {
                            ghost.direction = LivingEntity.Direction.LEFT;
                        } else if (availableDirectionRight) {
                            ghost.direction = LivingEntity.Direction.RIGHT;
                        } else if (yDistanceFromPacman > 0 && availableDirectionUp) {
                            ghost.direction = LivingEntity.Direction.UP;
                        } else if (availableDirectionDown) {
                            ghost.direction = LivingEntity.Direction.DOWN;
                        } else if (availableDirectionLeft) {
                            ghost.direction = LivingEntity.Direction.LEFT;
                        } else if (availableDirectionUp) {
                            ghost.direction = LivingEntity.Direction.UP;
                        }
                    } else {
                        if (yDistanceFromPacman > 0 && availableDirectionUp) {
                            ghost.direction = LivingEntity.Direction.UP;
                        } else if (availableDirectionDown) {
                            ghost.direction = LivingEntity.Direction.DOWN;
                        } else if (xDistanceFromPacman > 0 && availableDirectionLeft) {
                            ghost.direction = LivingEntity.Direction.LEFT;
                        } else if (availableDirectionRight) {
                            ghost.direction = LivingEntity.Direction.RIGHT;
                        } else if (availableDirectionUp) {
                            ghost.direction = LivingEntity.Direction.UP;
                        } else if (availableDirectionLeft) {
                            ghost.direction = LivingEntity.Direction.LEFT;
                        }
                    }
                }
            }
            //  ghost.timeDecideDirection = System.nanoTime();
        }
    }

    private boolean donMoveToBase(int xDistanceFromPacman) {
        if (ghost.x == 200 && ghost.y == 140) {
            if (xDistanceFromPacman > 0 && availableDirectionLeft) {
                ghost.direction = LivingEntity.Direction.LEFT;
                return true;
            } else if (availableDirectionRight) {
                ghost.direction = LivingEntity.Direction.RIGHT;
                return true;
            }
        }
        return false;
    }

    private void findAvailableDirection() {
        availableDirectionDown = availableThisDirection(LivingEntity.Direction.DOWN);
        availableDirectionLeft = availableThisDirection(LivingEntity.Direction.LEFT);
        availableDirectionRight = availableThisDirection(LivingEntity.Direction.RIGHT);
        availableDirectionUp = availableThisDirection(LivingEntity.Direction.UP);
    }

    private boolean availableThisDirection(LivingEntity.Direction direction) {
        return ghost.canMoveThisDirection(direction);
    }
}