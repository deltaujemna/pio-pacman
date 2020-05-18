import java.util.Random;

public class TrackPacman {
    private final Ghost ghost;

    private boolean availableDirectionUp;
    private boolean availableDirectionDown;
    private boolean availableDirectionRight;
    private boolean availableDirectionLeft;
    boolean pointReached=false;

    public TrackPacman(Ghost ghost) {
        this.ghost = ghost;
    }

    public void trackPacman() {
        findAvailableDirectory();
        if (!samePath()) {
            dontTurnBack(ghost.direction);
            int ghostNumber = ghost.ghostNumber;
            if (countAvailableDirection() > 2) {
                decideDirectionGhostNum(ghostNumber);
            } else if (!ghost.canMoveThisDirection(ghost.direction)) {
                decideDirectionGhostNum(ghostNumber);
            }
        }
    }

    private void dontTurnBack(LivingEntity.Direction direction) {
        if (direction == LivingEntity.Direction.LEFT) {
            availableDirectionRight = false;
        } else if (direction == LivingEntity.Direction.RIGHT) {
            availableDirectionLeft = false;
        } else if (direction == LivingEntity.Direction.UP) {
            availableDirectionDown = false;
        } else if (direction == LivingEntity.Direction.DOWN) {
            availableDirectionUp = false;
        }
    }

    private void decideDirectionGhostNum(int ghostNumber) {
        if (ghostNumber == 1) {
            decideDirection1();
        } else if (ghostNumber == 2) {
            decideDirection2();
        } else if (ghostNumber == 3) {
            decideDirection3();
        } else if (ghostNumber == 4) {
            decideDirection4();
        }
    }

    private boolean samePath() {
        if (ghost.x == ghost.pacmanX || ghost.y == ghost.pacmanY) {
            smartDicideDirection();
            return true;
        }
        return false;
    }


    // red ghost
    private void decideDirection1() {
        Random rand = new Random();
        int timesRandomDirector = 0;
        while (timesRandomDirector < 3) {
            int tempDirection = rand.nextInt(4);
            if (tempDirection == 0 && availableDirectionRight) {
                ghost.direction = LivingEntity.Direction.RIGHT;
                return;
            } else if (tempDirection == 1 && availableDirectionUp) {
                ghost.direction = LivingEntity.Direction.UP;
                return;
            } else if (tempDirection == 2 && availableDirectionDown) {
                ghost.direction = LivingEntity.Direction.DOWN;
                return;
            } else if (tempDirection == 3 && availableDirectionLeft) {
                ghost.direction = LivingEntity.Direction.LEFT;
                return;
            }
            timesRandomDirector++;
        }
        smartDicideDirection();

    }

    // pink ghost
    public void decideDirection2() {
        if (ghost.canMoveThisDirection(ghost.pacmanDirection)) {
            ghost.direction = ghost.pacmanDirection;
        } else if (ghost.canMoveThisDirection(ghost.pacmanDirectionFuture)) {
            ghost.direction = ghost.pacmanDirectionFuture;
        } else {
            smartDicideDirection();
        }
    }

    // orange ghost
    public void decideDirection3() {
        decideDirection1();
    }

    private void smartDicideDirection() {
        boolean isLeft;
        boolean isUp;
        int xDistanceFromPacman = ghost.x - ghost.pacmanX;
        int yDistanceFromPacman = ghost.y - ghost.pacmanY;
        isLeft = xDistanceFromPacman > 0;
        isUp = yDistanceFromPacman > 0;
        if (!donMoveToBase(xDistanceFromPacman)) {
            if ((xDistanceFromPacman * xDistanceFromPacman) > (yDistanceFromPacman * yDistanceFromPacman)) {
                if (isLeft) {
                    if (availableDirectionLeft) {
                        ghost.direction = LivingEntity.Direction.LEFT;
                    } else if (!moveDownOrUp(isUp)) {
                        if (availableDirectionRight) {
                            ghost.direction = LivingEntity.Direction.RIGHT;
                        }
                    }
                } else {
                    if (availableDirectionRight) {
                        ghost.direction = LivingEntity.Direction.RIGHT;
                    } else if (!moveDownOrUp(isUp)) {
                        if (availableDirectionLeft) {
                            ghost.direction = LivingEntity.Direction.LEFT;
                        }
                    }
                }
            } else {
                if (isUp) {
                    if (availableDirectionUp) {
                        ghost.direction = LivingEntity.Direction.UP;
                    } else if (!moveLeftOrRight(isLeft)) {
                        if (availableDirectionDown) {
                            ghost.direction = LivingEntity.Direction.DOWN;
                        }
                    }
                } else {
                    if (availableDirectionDown) {
                        ghost.direction = LivingEntity.Direction.DOWN;
                    } else if (!moveLeftOrRight(isLeft)) {
                        if (availableDirectionUp) {
                            ghost.direction = LivingEntity.Direction.UP;
                        }
                    }
                }

            }
        }
    }

    private boolean moveLeftOrRight(boolean isLeft) {
        if (isLeft && availableDirectionLeft) {
            ghost.direction = LivingEntity.Direction.LEFT;
        } else if (availableDirectionRight) {
            ghost.direction = LivingEntity.Direction.RIGHT;
        } else if (availableDirectionLeft) {
            ghost.direction = LivingEntity.Direction.LEFT;
        } else {
            return false;
        }
        return true;
    }

    private boolean moveDownOrUp(boolean isUp) {
        if (isUp && availableDirectionUp) {
            ghost.direction = LivingEntity.Direction.UP;
        } else if (availableDirectionDown) {
            ghost.direction = LivingEntity.Direction.DOWN;
        } else if (availableDirectionUp) {
            ghost.direction = LivingEntity.Direction.UP;
        } else {
            return false;
        }
        return true;
    }

    // yellow ghost
    private void decideDirection4() {
        int xDistanceFromPacman = ghost.x - ghost.pacmanX;
        int yDistanceFromPacman = ghost.y - ghost.pacmanY;

        if (!donMoveToBase(xDistanceFromPacman)) {
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

    private void findAvailableDirectory() {
        availableDirectionDown = availableThisDirectory(LivingEntity.Direction.DOWN);
        availableDirectionLeft = availableThisDirectory(LivingEntity.Direction.LEFT);
        availableDirectionRight = availableThisDirectory(LivingEntity.Direction.RIGHT);
        availableDirectionUp = availableThisDirectory(LivingEntity.Direction.UP);

    }

    private int countAvailableDirection() {
        int tmp = 0;
        if (availableDirectionUp) tmp++;

        if (availableDirectionDown) tmp++;

        if (availableDirectionLeft) tmp++;

        if (availableDirectionRight) tmp++;

        return tmp;
    }

    private boolean availableThisDirectory(LivingEntity.Direction direction) {
        return ghost.canMoveThisDirection(direction);
    }

    public void escapeFromPacman() {
        int xDistanceFromPacman = ghost.x - ghost.pacmanX;
        int yDistanceFromPacman = ghost.y - ghost.pacmanY;
        findAvailableDirectory();
        if (ghost.x != ghost.pacmanX && ghost.y != ghost.pacmanY) {
            dontTurnBack(ghost.direction);
        }
        if ((xDistanceFromPacman * xDistanceFromPacman) > (yDistanceFromPacman * yDistanceFromPacman)) {
            if (xDistanceFromPacman > 0 && availableDirectionRight) {
                ghost.direction = LivingEntity.Direction.RIGHT;
            } else if (yDistanceFromPacman > 0 && availableDirectionDown) {
                ghost.direction = LivingEntity.Direction.DOWN;
            } else if (availableDirectionUp) {
                ghost.direction = LivingEntity.Direction.UP;
            } else if (availableDirectionDown) {
                ghost.direction = LivingEntity.Direction.DOWN;
            } else if (availableDirectionLeft) {
                ghost.direction = LivingEntity.Direction.LEFT;
            } else if (availableDirectionRight) {
                ghost.direction = LivingEntity.Direction.RIGHT;

            }
        } else {
            if (yDistanceFromPacman > 0 && availableDirectionDown) {
                ghost.direction = LivingEntity.Direction.DOWN;
            } else if (xDistanceFromPacman > 0 && availableDirectionRight) {
                ghost.direction = LivingEntity.Direction.RIGHT;
            } else if (availableDirectionLeft) {
                ghost.direction = LivingEntity.Direction.LEFT;
            } else if (availableDirectionRight) {
                ghost.direction = LivingEntity.Direction.RIGHT;
            } else if (availableDirectionDown) {
                ghost.direction = LivingEntity.Direction.DOWN;
            } else if (availableDirectionUp) {
                ghost.direction = LivingEntity.Direction.UP;
            }
        }


    }
    public void goToCage() {
        findAvailableDirectory();
        if (availableDirectionUp && !pointReached) {
            ghost.direction = LivingEntity.Direction.UP;
            pointReached = true;
        }
        if (availableDirectionDown && !pointReached) {
            ghost.direction = LivingEntity.Direction.DOWN;
            pointReached = true;
        }
        if (availableDirectionRight && !pointReached) {
            ghost.direction = LivingEntity.Direction.RIGHT;
            pointReached = true;
        }
        if (availableDirectionLeft && !pointReached) {
            ghost.direction = LivingEntity.Direction.LEFT;
            pointReached = true;
        }
        try {
            if (ghost.toCells(ghost.x) == 18 && ghost.toCells(ghost.y) == 16) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;

            } else if (ghost.toCells(ghost.x) == 14 && ghost.toCells(ghost.y) == 16) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 14 && ghost.toCells(ghost.y) == 8) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            } else if (ghost.toCells(ghost.x) == 14 && ghost.toCells(ghost.y) == 12) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 12 && ghost.toCells(ghost.y) == 8) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 12 && ghost.toCells(ghost.y) == 6) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            } else if (ghost.toCells(ghost.x) == 9 && ghost.toCells(ghost.y) == 6) {
                if (availableDirectionDown)
                    ghost.direction = LivingEntity.Direction.DOWN;
            } else if (ghost.toCells(ghost.x) == 18 && ghost.toCells(ghost.y) == 18) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 18 && ghost.toCells(ghost.y) == 14) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 18 && ghost.toCells(ghost.y) == 12) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            } else if (ghost.toCells(ghost.x) == 14 && ghost.toCells(ghost.y) == 16) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 14 && ghost.toCells(ghost.y) == 18) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 12 && ghost.toCells(ghost.y) == 18) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            } else if (ghost.toCells(ghost.x) == 14 && ghost.toCells(ghost.y) == 8) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            } else if (ghost.toCells(ghost.x) == 14 && ghost.toCells(ghost.y) == 4) {
                if (availableDirectionDown)
                    ghost.direction = LivingEntity.Direction.DOWN;
            } else if (ghost.toCells(ghost.x) == 14 && ghost.toCells(ghost.y) == 0) {
                if (availableDirectionDown)
                    ghost.direction = LivingEntity.Direction.DOWN;
            } else if (ghost.toCells(ghost.x) == 14 && ghost.toCells(ghost.y) == 2) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            } else if (ghost.toCells(ghost.x) == 18 && ghost.toCells(ghost.y) == 0) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            } else if (ghost.toCells(ghost.x) == 18 && ghost.toCells(ghost.y) == 2) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            } else if (ghost.toCells(ghost.x) == 18 && ghost.toCells(ghost.y) == 4) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            } else if (ghost.toCells(ghost.x) == 10 && ghost.toCells(ghost.y) == 0) {
                if (availableDirectionDown)
                    ghost.direction = LivingEntity.Direction.DOWN;
            } else if (ghost.toCells(ghost.x) == 10 && ghost.toCells(ghost.y) == 2) {
                if (availableDirectionRight)
                    ghost.direction = LivingEntity.Direction.RIGHT;
            } else if (ghost.toCells(ghost.x) == 12 && ghost.toCells(ghost.y) == 2) {
                if (availableDirectionDown)
                    ghost.direction = LivingEntity.Direction.DOWN;
            } else if (ghost.toCells(ghost.x) == 12 && ghost.toCells(ghost.y) == 4) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            } else if (ghost.toCells(ghost.x) == 10 && ghost.toCells(ghost.y) == 4) {
                if (availableDirectionDown)
                    ghost.direction = LivingEntity.Direction.DOWN;
            } else if (ghost.toCells(ghost.x) == 10 && ghost.toCells(ghost.y) == 6) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            } else if (ghost.toCells(ghost.x) == 12 && ghost.toCells(ghost.y) == 10) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 12 && ghost.toCells(ghost.y) == 12) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 12 && ghost.toCells(ghost.y) == 4) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            } else if (ghost.toCells(ghost.x) == 10 && ghost.toCells(ghost.y) == 14) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 10 && ghost.toCells(ghost.y) == 12) {
                if (availableDirectionRight)
                    ghost.direction = LivingEntity.Direction.RIGHT;
            } else if (ghost.toCells(ghost.x) == 12 && ghost.toCells(ghost.y) == 16) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 10 && ghost.toCells(ghost.y) == 18) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 10 && ghost.toCells(ghost.y) == 16) {
                if (availableDirectionRight)
                    ghost.direction = LivingEntity.Direction.RIGHT;
            } else if (ghost.toCells(ghost.x) == 8 && ghost.toCells(ghost.y) == 18) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 8 && ghost.toCells(ghost.y) == 16) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            } else if (ghost.toCells(ghost.x) == 2 && ghost.toCells(ghost.y) == 14) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            } else if (ghost.toCells(ghost.x) == 8 && ghost.toCells(ghost.y) == 2) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            } else if (ghost.toCells(ghost.x) == 0 && ghost.toCells(ghost.y) == 0) {
                if (availableDirectionDown)
                    ghost.direction = LivingEntity.Direction.DOWN;
            } else if (ghost.toCells(ghost.x) == 4 && ghost.toCells(ghost.y) == 0) {
                if (availableDirectionDown)
                    ghost.direction = LivingEntity.Direction.DOWN;
            } else if (ghost.toCells(ghost.x) == 4 && ghost.toCells(ghost.y) == 4) {
                if (availableDirectionDown)
                    ghost.direction = LivingEntity.Direction.DOWN;
            } else if (ghost.toCells(ghost.x) == 0 && ghost.toCells(ghost.y) == 2) {
                if (availableDirectionDown)
                    ghost.direction = LivingEntity.Direction.DOWN;
            } else if (ghost.toCells(ghost.x) == 4 && ghost.toCells(ghost.y) == 2) {
                if (availableDirectionRight)
                    ghost.direction = LivingEntity.Direction.RIGHT;
            } else if (ghost.toCells(ghost.x) == 6 && ghost.toCells(ghost.y) == 2) {
                if (availableDirectionDown)
                    ghost.direction = LivingEntity.Direction.DOWN;
            } else if (ghost.toCells(ghost.x) == 8 && ghost.toCells(ghost.y) == 4) {
                if (availableDirectionDown)
                    ghost.direction = LivingEntity.Direction.DOWN;
            } else if (ghost.toCells(ghost.x) == 0 && ghost.toCells(ghost.y) == 4) {
                if (availableDirectionRight)
                    ghost.direction = LivingEntity.Direction.RIGHT;
            } else if (ghost.toCells(ghost.x) == 4 && ghost.toCells(ghost.y) == 8) {
                if (availableDirectionRight)
                    ghost.direction = LivingEntity.Direction.RIGHT;
            } else if (ghost.toCells(ghost.x) == 6 && ghost.toCells(ghost.y) == 6) {
                if (availableDirectionRight)
                    ghost.direction = LivingEntity.Direction.RIGHT;
            } else if (ghost.toCells(ghost.x) == 6 && ghost.toCells(ghost.y) == 4) {
                if (availableDirectionRight)
                    ghost.direction = LivingEntity.Direction.RIGHT;
            } else if (ghost.toCells(ghost.x) == 0 && ghost.toCells(ghost.y) == 12) {
                if (availableDirectionRight)
                    ghost.direction = LivingEntity.Direction.RIGHT;
            } else if (ghost.toCells(ghost.x) == 0 && ghost.toCells(ghost.y) == 16) {
                if (availableDirectionRight)
                    ghost.direction = LivingEntity.Direction.RIGHT;
            } else if (ghost.toCells(ghost.x) == 4 && ghost.toCells(ghost.y) == 12) {
                if (availableDirectionRight)
                    ghost.direction = LivingEntity.Direction.RIGHT;
            } else if (ghost.toCells(ghost.x) == 10 && ghost.toCells(ghost.y) == 18) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 6 && ghost.toCells(ghost.y) == 12) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 6 && ghost.toCells(ghost.y) == 8) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 4 && ghost.toCells(ghost.y) == 16) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 0 && ghost.toCells(ghost.y) == 18) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 2 && ghost.toCells(ghost.y) == 16) {
                if (availableDirectionRight)
                    ghost.direction = LivingEntity.Direction.RIGHT;
            } else if (ghost.toCells(ghost.x) == 0 && ghost.toCells(ghost.y) == 14) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 6 && ghost.toCells(ghost.y) == 16) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 4 && ghost.toCells(ghost.y) == 14) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 8 && ghost.toCells(ghost.y) == 14) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 8 && ghost.toCells(ghost.y) == 12) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            } else if (ghost.toCells(ghost.x) == 14 && ghost.toCells(ghost.y) == 14) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 16 && ghost.toCells(ghost.y) == 14) {
                if (availableDirectionRight)
                    ghost.direction = LivingEntity.Direction.RIGHT;
            } else if (ghost.toCells(ghost.x) == 6 && ghost.toCells(ghost.y) == 14) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            } else if (ghost.toCells(ghost.x) == 6 && ghost.toCells(ghost.y) == 10) {
                if (availableDirectionUp)
                    ghost.direction = LivingEntity.Direction.UP;
            } else if (ghost.toCells(ghost.x) == 8 && ghost.toCells(ghost.y) == 6) {
                if (availableDirectionRight)
                    ghost.direction = LivingEntity.Direction.RIGHT;
            } else if (ghost.toCells(ghost.x) == 8 && ghost.toCells(ghost.y) == 0) {
                if (availableDirectionDown)
                    ghost.direction = LivingEntity.Direction.DOWN;
            } else if (ghost.toCells(ghost.x) == 12 && ghost.toCells(ghost.y) == 14) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            } else if (ghost.toCells(ghost.x) == 9 && ghost.toCells(ghost.y) == 8) {
                ghost.speed = 0;
            } else if (ghost.toCells(ghost.x) == 10 && ghost.toCells(ghost.y) == 8) {
                if (availableDirectionLeft)
                    ghost.direction = LivingEntity.Direction.LEFT;
            }else {
                dontTurnBack(ghost.direction);
                decideDirection1();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
