# Zilvervisjes: Strikes Again

Top-down survival game ontwikkeld als eindopdracht voor het vak
**Object Georiënteerd Programmeren (2025 P3A)** aan de Hogeschool van
Arnhem en Nijmegen.

## Spelconcept

Speel als archivaris in Het Depot van het Regionaal Archief Nijmegen.
Bescherm de waardevolle archiefdocumenten — dossiers en historische boeken —
tegen een groeiende invasie van zilvervisjes. Schiet ze neer met je
insectenspray of UV-lamp, en overleef zo lang mogelijk.

## Besturing

| Toets         | Actie                       |
|---------------|-----------------------------|
| Pijltjes      | Beweeg in vier richtingen   |
| Spatie        | Vuur insectenspray af       |
| X             | Vuur UV-lamp af             |
| Esc           | Pauzeer / hervat het spel   |
| Enter         | Start het spel              |

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

Of open het project in IntelliJ IDEA en run de `ZilvervisjesStrikesAgain`
klasse rechtstreeks.

## Projectstructuur

src/main/java/nl/han/serdaraydemir/zilvervisjes/
├── ZilvervisjesStrikesAgain.java    # hoofdklasse (extends YaegerGame)
├── scenes/                           # spelschermen
│   ├── StartScene.java
│   └── GameScene.java
└── entities/                         # speelobjecten
├── Archivist.java
├── Hole.java
├── documents/                    # te beschermen documenten
├── silverfish/                   # vier soorten zilvervisjes
├── projectiles/                  # afgevuurde projectielen
└── weapons/                      # wapens van de Archivaris

## Technische achtergrond

Het spel is gebouwd met de [Yaeger](https://github.com/han-yaeger/yaeger)
game engine. Belangrijke OO-principes die in dit project worden toegepast:

- **Overerving** — abstracte basisklassen (`Silverfish`, `Document`,
  `Weapon`, `Projectile`) met concrete subklassen
- **Polymorfie** — bijvoorbeeld `Weapon.createProjectile()` retourneert
  verschillende `Projectile`-types per subklasse
- **Encapsulatie** — interne staat afgeschermd via `private final` velden
- **Compositie** — `Archivist` bevat een `Weapon` (has-a relatie)

## Auteur

Serdar Aydemir
Studentnummer 2157400  
Begeleidend docent: Ronald Schellekens