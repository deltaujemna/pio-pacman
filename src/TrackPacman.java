import java.util.Random;

public class TrackPacman {
    private Ghost ghost;

    private boolean availableDirectoryUp;
    private boolean availableDirectoryDown;
    private boolean availableDirectoryRight;
    private boolean availableDirectoryLeft;


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
        if (!samePath()) {
            if (!ghost.canMoveThisDirection(ghost.direction)) {
                Random rand = new Random();
                int timesRandomDirector = 0;
                while (timesRandomDirector < 10) {
                    int tempDirection = rand.nextInt(4);
                    if (tempDirection == 0)
                        ghost.direction = LivingEntity.Direction.RIGHT;
                    if (tempDirection == 1)
                        ghost.direction = LivingEntity.Direction.UP;
                    if (tempDirection == 2)
                        ghost.direction = LivingEntity.Direction.DOWN;
                    if (tempDirection == 3)
                        ghost.direction = LivingEntity.Direction.LEFT;
                    if (ghost.canMoveThisDirection(ghost.direction)) {
                        return;
                    }
                    timesRandomDirector++;
                }
                smartDicideDirection();

            }
        }

    }

    // pink ghost
    public void decideDirection2() {
        if (!samePath()) {
            if (!ghost.canMoveThisDirection(ghost.direction)) {
                if (ghost.canMoveThisDirection(ghost.pacmanDirectory)) {
                    ghost.direction = ghost.pacmanDirectory;
                } else if (ghost.canMoveThisDirection(ghost.pacmanDirectoryFuture)) {
                    ghost.direction = ghost.pacmanDirectoryFuture;
                } else {
                    smartDicideDirection();
                }
            }
        }
    }

    // orange ghost
    public void decideDirection3() {
        if(!ghost.canMoveThisDirection(ghost.direction)){
            smartDicideDirection();
        }
    }

    private void smartDicideDirection() {
        findAvailableDirectory();
        boolean isLeft;
        boolean isUp;
        int xDistanceFromPacman = ghost.x - ghost.pacmanX;
        int yDistanceFromPacman = ghost.y - ghost.pacmanY;
        isLeft = xDistanceFromPacman > 0;
        isUp = yDistanceFromPacman > 0;
        if (!donMoveToBase(xDistanceFromPacman)) {
            if ((xDistanceFromPacman * xDistanceFromPacman) > (yDistanceFromPacman * yDistanceFromPacman)) {
                if (isLeft) {
                    if (availableDirectoryLeft) {
                        ghost.direction = LivingEntity.Direction.LEFT;
                    } else {
                        moveDownorUp(isUp);
                    }
                } else {
                    if (availableDirectoryRight) {
                        ghost.direction = LivingEntity.Direction.RIGHT;
                    } else {
                        moveDownorUp(isUp);
                    }
                }
            } else {
                if (isUp) {
                    if (availableDirectoryUp) {
                        ghost.direction = LivingEntity.Direction.UP;
                    } else {
                        moveLeftorRight(isLeft);
                    }
                } else {
                    if (availableDirectoryDown) {
                        ghost.direction = LivingEntity.Direction.DOWN;
                    } else {
                        moveLeftorRight(isLeft);
                    }
                }

            }
        }
    }

    private void moveLeftorRight(boolean isLeft) {
        if (isLeft && availableDirectoryLeft) {
            ghost.direction = LivingEntity.Direction.LEFT;
        } else if (availableDirectoryRight) {
            ghost.direction = LivingEntity.Direction.RIGHT;
        } else if (availableDirectoryLeft) {
            ghost.direction = LivingEntity.Direction.LEFT;
        }
    }

    private void moveDownorUp(boolean isUp) {
        if (isUp && availableDirectoryUp) {
            ghost.direction = LivingEntity.Direction.UP;
        } else if (availableDirectoryDown) {
            ghost.direction = LivingEntity.Direction.DOWN;
        } else if (availableDirectoryUp) {
            ghost.direction = LivingEntity.Direction.UP;
        }
    }

    // yellow ghost
    private void decideDirection4() {
        int xDistanceFromPacman = ghost.x - ghost.pacmanX;
        int yDistanceFromPacman = ghost.y - ghost.pacmanY;

        if (!donMoveToBase(xDistanceFromPacman)) {
            if (!samePath()) {
                findAvailableDirectory();
                if (!ghost.canMoveThisDirection(ghost.direction)) {
                    if ((xDistanceFromPacman * xDistanceFromPacman) > (yDistanceFromPacman * yDistanceFromPacman)) {
                        if (xDistanceFromPacman > 0 && availableDirectoryLeft) {
                            ghost.direction = LivingEntity.Direction.LEFT;
                        } else if (availableDirectoryRight) {
                            ghost.direction = LivingEntity.Direction.RIGHT;
                        } else if (yDistanceFromPacman > 0 && availableDirectoryUp) {
                            ghost.direction = LivingEntity.Direction.UP;
                        } else if (availableDirectoryDown) {
                            ghost.direction = LivingEntity.Direction.DOWN;
                        } else if (availableDirectoryLeft) {
                            ghost.direction = LivingEntity.Direction.LEFT;
                        } else if (availableDirectoryUp) {
                            ghost.direction = LivingEntity.Direction.UP;
                        }
                    } else {
                        if (yDistanceFromPacman > 0 && availableDirectoryUp) {
                            ghost.direction = LivingEntity.Direction.UP;
                        } else if (availableDirectoryDown) {
                            ghost.direction = LivingEntity.Direction.DOWN;
                        } else if (xDistanceFromPacman > 0 && availableDirectoryLeft) {
                            ghost.direction = LivingEntity.Direction.LEFT;
                        } else if (availableDirectoryRight) {
                            ghost.direction = LivingEntity.Direction.RIGHT;
                        } else if (availableDirectoryUp) {
                            ghost.direction = LivingEntity.Direction.UP;
                        } else if (availableDirectoryLeft) {
                            ghost.direction = LivingEntity.Direction.LEFT;

                        }
                    }
                }
            }
        }


    }

    private boolean donMoveToBase(int xDistanceFromPacman) {
        if (ghost.x == 200 && ghost.y == 140) {
            if (xDistanceFromPacman > 0 && availableDirectoryLeft) {
                ghost.direction = LivingEntity.Direction.LEFT;
                return true;
            } else if (availableDirectoryRight) {
                ghost.direction = LivingEntity.Direction.RIGHT;
                return true;
            }
        }
        return false;
    }

    private void findAvailableDirectory() {
        availableDirectoryDown = availableThisDirectory(LivingEntity.Direction.DOWN);
        availableDirectoryLeft = availableThisDirectory(LivingEntity.Direction.LEFT);
        availableDirectoryRight = availableThisDirectory(LivingEntity.Direction.RIGHT);
        availableDirectoryUp = availableThisDirectory(LivingEntity.Direction.UP);

    }

    private boolean availableThisDirectory(LivingEntity.Direction direction) {
        return ghost.canMoveThisDirection(direction);
    }

    public void escapeFromPacman() {
        int xDistanceFromPacman = ghost.x - ghost.pacmanX;
        int yDistanceFromPacman = ghost.y - ghost.pacmanY;
        if (ghost.x == ghost.pacmanX || ghost.y == ghost.pacmanY) {
            findAvailableDirectory();
            if ((xDistanceFromPacman * xDistanceFromPacman) > (yDistanceFromPacman * yDistanceFromPacman)) {
                if (xDistanceFromPacman > 0 && availableDirectoryRight) {
                    ghost.direction = LivingEntity.Direction.RIGHT;
                } else if (yDistanceFromPacman > 0 && availableDirectoryDown) {
                    ghost.direction = LivingEntity.Direction.DOWN;
                } else if (availableDirectoryUp) {
                    ghost.direction = LivingEntity.Direction.UP;
                } else if (availableDirectoryDown) {
                    ghost.direction = LivingEntity.Direction.DOWN;
                } else if (availableDirectoryLeft) {
                    ghost.direction = LivingEntity.Direction.LEFT;
                } else if (availableDirectoryRight) {
                    ghost.direction = LivingEntity.Direction.RIGHT;

                }
            } else {
                if (yDistanceFromPacman > 0 && availableDirectoryDown) {
                    ghost.direction = LivingEntity.Direction.DOWN;
                } else if (xDistanceFromPacman > 0 && availableDirectoryRight) {
                    ghost.direction = LivingEntity.Direction.RIGHT;
                } else if (availableDirectoryLeft) {
                    ghost.direction = LivingEntity.Direction.LEFT;
                } else if (availableDirectoryRight) {
                    ghost.direction = LivingEntity.Direction.RIGHT;
                } else if (availableDirectoryDown) {
                    ghost.direction = LivingEntity.Direction.DOWN;
                } else if (availableDirectoryUp) {
                    ghost.direction = LivingEntity.Direction.UP;
                }
            }
        } else {
            decideDirection1();
        }


    }

}
