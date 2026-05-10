# Zilvervisjes: Strikes Again

Top-down survival game ontwikkeld als eindopdracht voor het vak **Object Georiënteerd Programmeren** aan de Hogeschool van Arnhem en Nijmegen.

## Spelconcept

Speel als archivaris in Het Depot van het Regionaal Archief Nijmegen. Bescherm de waardevolle archiefdocumenten — dossiers en historische boeken — tegen een groeiende invasie van zilvervisjes. Schiet ze neer met je insectenspray of UV-lamp, en overleef zo lang mogelijk.

## Besturing

| Toets | Actie |
| --- | --- |
| Pijltjes | Beweeg de archivaris in vier richtingen |
| Spatie | Vuur de insectenspray af |
| X | Vuur de UV-lamp af |
| Enter | Start het spel (vanuit het hoofdmenu of het game-overscherm) |
| H | Open het hulpscherm (vanuit het hoofdmenu) |
| M / Esc | Terug naar het hoofdmenu (vanuit het hulpscherm) |

## Vereisten

- Java 21 of hoger (getest met JDK 25)
- Maven 3.6+
- Yaeger 2024.2025 (wordt automatisch via Maven opgehaald)

## Installatie en uitvoeren

```bash
git clone https://github.com/SerdarAydemir/zilvervisjes-strikes-again.git
cd zilvervisjes-strikes-again
mvn clean install
mvn exec:java -Dexec.mainClass="nl.han.serdaraydemir.zilvervisjes.ZilvervisjesStrikesAgain"
```

Of open het project in IntelliJ IDEA en run de `ZilvervisjesStrikesAgain` klasse rechtstreeks.

## Projectstructuur

src/main/java/nl/han/serdaraydemir/zilvervisjes/
├── ZilvervisjesStrikesAgain.java     # hoofdklasse (extends YaegerGame)
├── scenes/                            # spelschermen
│   ├── StartScene.java
│   ├── HelpScene.java
│   ├── GameScene.java
│   └── GameOverScene.java
├── game/
│   └── Phase.java                     # spelfases (Kalm, Gemiddeld, Extreem)
├── spawners/
│   └── SilverfishSpawner.java         # automatische spawn-logica
└── entities/                          # speelobjecten
├── Archivist.java                 # de speler
├── Hole.java                      # gaten waaruit zilvervisjes verschijnen
├── DepotFloor.java                # achtergrond
├── dashboard/                     # score, fase, tijd
├── documents/                     # te beschermen documenten
├── menu/                          # decoratieve elementen voor het hoofdmenu
├── overlay/                       # openingssequentie
├── projectiles/                   # afgevuurde projectielen
├── silverfish/                    # vier soorten zilvervisjes
└── weapons/                       # wapens van de archivaris


## Technische achtergrond

Het spel is gebouwd met de [Yaeger](https://github.com/han-yaeger/yaeger) game engine. Belangrijke OO-principes die in dit project worden toegepast:

- **Overerving** — abstracte basisklassen (`Silverfish`, `Document`, `Weapon`, `Projectile`) met concrete subklassen.
- **Polymorfie** — bijvoorbeeld `Weapon.createProjectile()` retourneert per subklasse een ander `Projectile`-type, en `Silverfish.refreshTarget()` wordt door `Paperfish` overschreven om dossiers boven boeken te prioriteren.
- **Encapsulatie** — interne staat afgeschermd via `private` of `private final` velden; toegang verloopt via methoden.
- **Compositie** — `Archivist` bezit twee wapens via een has-a relatie; `GameScene` bezit een `SilverfishSpawner`, een `OpeningSequence` en het dashboard.
- **Observer-pattern** — silverfish en documenten communiceren met `GameScene` via `Consumer<...>`-callbacks, zodat lagen losjes gekoppeld blijven.

## Documentatie

Het volledige functioneel ontwerp (FO v0.2) en technisch ontwerp (TO v0.1) zijn afzonderlijk ingeleverd via Brightspace.

## Auteur

Serdar Aydemir
Studentnummer 2157400
Begeleidend docent: Ronald Schellekens