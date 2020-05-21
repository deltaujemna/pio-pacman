public class MovePacman {
    private final Pacman pacman;

    public MovePacman(Pacman pacman) {
        this.pacman = pacman;
    }

    // porusza w ustalonym kierunku
    public void move() {
        if (!teleport() && canMoveDirectionFutureAndDirection()) {
            pacman.setSpeed(pacman.direction);
        }
    }

    private boolean teleport() {
        if (pacman.y == 180) {
            if (pacman.x < 60) {
                turboInTeleport();
                if (pacman.x <= 20) {
                    pacman.teleport(380, 180);
                }
                return true;
            } else if (pacman.x > 320) {
                turboInTeleport();
                if (pacman.x >= 380) {
                    pacman.teleport(20, 180);
                }
                return true;
            }
        }
        return false;
    }

    private void turboInTeleport() {
        int turbo = 2;
        if (pacman.direction == LivingEntity.Direction.LEFT) {
            pacman.x -= turbo;
        } else if (pacman.direction == LivingEntity.Direction.RIGHT) {
            pacman.x += turbo;
        }
    }

    private boolean canMoveDirectionFutureAndDirection() {
        if (pacman.canMoveThisDirection(pacman.directionFuture)) {
            pacman.direction = pacman.directionFuture;
            return true;
        } else {
            return pacman.canMoveThisDirection(pacman.direction);
        }
    }
}
