package nl.han.serdaraydemir.zilvervisjes.entities.silverfish;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Document;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Dossier;

import java.util.List;

public class Paperfish extends Silverfish {

    private static final String SPRITE_PATH = "sprites/paperfish.png";
    private static final double WIDTH = 22;
    private static final double HEIGHT = 44;

    private static final double SPEED = 1.8;
    private static final int HEALTH = 1;
    private static final int POINTS = 20;
    private static final int ATTACK_DAMAGE = 3;

    public Paperfish(Coordinate2D location, List<Document> targets) {
        super(SPRITE_PATH, new Size(WIDTH, HEIGHT), location, targets, HEALTH, POINTS);

        Document closest = findClosestDocumentTo(location);
        if (closest != null) {
            double angle = location.angleTo(closest.getAnchorLocation());
            aimAt(angle);
        }
    }

    @Override
    protected double getSpeedValue() {
        return SPEED;
    }

    @Override
    protected int getAttackDamage() {
        return ATTACK_DAMAGE;
    }

    @Override
    protected Document findClosestDocumentTo(Coordinate2D from) {
        Document closestDossier = null;
        double minDistance = Double.MAX_VALUE;

        for (Document doc : getTargets()) {
            if (doc.isDestroyed()) continue;
            if (!(doc instanceof Dossier)) continue;
            double distance = from.distance(doc.getAnchorLocation());
            if (distance < minDistance) {
                minDistance = distance;
                closestDossier = doc;
            }
        }

        if (closestDossier != null) {
            return closestDossier;
        }
        return super.findClosestDocumentTo(from);
    }
}