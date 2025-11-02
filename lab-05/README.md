---

## 5 Summary of Implementation

This lab models an **ecosystem simulation** using **inheritance** and **polymorphism**.

- **Creature.java** → abstract superclass implementing `TurnTaker`
- **Plant.java** → extends Creature, adds composition via ReproductionBehavior
- **PinePlant**, **VinePlant**, **MossPlant** → subclasses with specific traits
- **Bird**, **Mammal**, **Fish** → non-plant creatures extending Creature
- **World & Tile** → control the environment grid
- **EcosystemSimulation.java** → reads configuration from `world-config.json`, runs 100 turns
- **Output** → written to `final-report.txt`

### Compile & Run
```cmd
javac -d ..\bin *.java
java -cp ..\bin EcosystemSimulation
