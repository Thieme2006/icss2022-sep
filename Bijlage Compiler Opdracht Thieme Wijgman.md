# Bijlage 1: Overzicht van behaalde requirements

| Student       | Thieme Wijgman   |
|---------------|------------------|
| Studentnummer | 2126329          |
| Datum         | 03-04-2026       |
| Docent        | Michel Koolwaaij |
| Klas          | ITA-CNI-A-f      |

# Overzicht

In deze bijlage is mijn beoordeling over mijn implementatie van de compiler opdracht, deze bestaat uit:

* Parser
* Checker
* Evaluator
* Generator
* Eigen implementatie

# Algemene eisen

| ID   | Omschrijving                                                                                                                | Prio | Punten | Status  | Toelichting                                                                                                                                                                          |
|------|-----------------------------------------------------------------------------------------------------------------------------|------|--------|---------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| AL01 | De code behoudt de package strucutur van de aangeleverde startcode. Toegevoegde code bevindt zich in de relevante packages. | Must | 0      | Behaald | Alle toegevoegde code is te vinden in de relevante classes of in dezelfde package structuur.                                                                                         |
| AL02 | Alle code compileert en is te bouwen met Mavan 3.6 of hoger, onder OpenJDK 13.                                              | Must | 0      | Behaald | De code is te compilen, alleen is OpenJDK 21 gebruikt voor de implementatie. **Hou hier dus rekening mee tijdens het opstarten**. Er is nagevraagd of Java 21 gebruikt mocht worden. |
| AL03 |                                                                                                                             |      |        |         |                                                                                                                                                                                      |

# Parser

| ID   | Omschrijving                                                                                                                                      | Prio | Punten | Status  | Toelichting                                                       |
|------|---------------------------------------------------------------------------------------------------------------------------------------------------|------|--------|---------|-------------------------------------------------------------------|
| PA00 | De parser dient zinvol gebruik te maken van jouw eigen implementatie van een stack generic voor `ASTNode` (VT: zie huiswerk `IHANStack<ASTNode>`) | Must | 0      | Behaald | HANStack<T> is geïmplementeerd en wordt gebruikt in `ASTListener` |
| PA01 |                                                                                                                                                   |      |        |         |                                                                   |
| PA02 |                                                                                                                                                   |      |        |         |                                                                   |
| PA03 |                                                                                                                                                   |      |        |         |                                                                   |
| PA04 |                                                                                                                                                   |      |        |         |                                                                   |
| PA05 |                                                                                                                                                   |      |        |         |                                                                   |

# Checker

| ID   | Omschrijving                                                                                                                                                                                                                                                                         | Prio   | Punten | Status                               |
|------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------|--------|--------------------------------------|
| CH00 | Minimaal vier van onderstaande checks **moeten** zijn geïmplementeerd                                                                                                                                                                                                                | Must   | 0      | Behaald                              |
| CH01 | Controleer of er geen variabelen worden gebruikt die niet gedefinieerd zijn.                                                                                                                                                                                                         | Should | 5      | Behaald                              |
| CH02 | Controleer of de operanden van de operaties plus en min van gelijk type zijn. Je mag geen pixels bij percentages optellen bijvoorbeeld. Controleer dat bij vermenigvuldigen minimaal een operand een scalaire waarde is. Zo mag `20% * 3` en `4 * 5` wel, maar mag `2px * 3px` niet. | Should | 5      | Behaald                              |
| CH03 | Controleer of er geen kleuren worden gebruikt in operaties (plus, min en keer).                                                                                                                                                                                                      | Should | 5      | Behaald                              |
| CH04 | Controleer of bij declaraties het type van de value klopt met de property. Declaraties zoals `width: #ff0000` of `color: 12px` zijn natuurlijk onzin.                                                                                                                                | Should | 5      | Behaald                              |
| CH05 | Controleer of de conditie bij een if-statement van het type boolean is (zowel bij een variabele-referentie als een boolean literal)                                                                                                                                                  | Should | 5      | Behaald                              |
| CH06 | Controleer of variabelen enkel binnen hun scope gebruikt worden                                                                                                                                                                                                                      | Must   | 5      | Behaald (Scopes if, stylerule, else) |

# Evaluator

| ID   | Omschrijving                                                                                                                                                                                                                                                                                                                                                                                                                       | Prio | Punten | Status  |
|------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------|--------|---------|
| TR01 | Evalueer expressies. Schrijf een transformatie in ```Evaluator``` die alle `Expression` knopen in de AST door een `Literal` knoop met de berekende waarde vervangt.                                                                                                                                                                                                                                                                | Must | 10     | Behaald |
| TR02 | Evalueer if/else expressies. Schrijf een transformatie in ```Evaluator``` die alle `IfClause`s uit de AST verwijdert. Wanneer de conditie van de `IfClause` `TRUE` is wordt deze vervangen door de body van het if-statement. Als de conditie `FALSE` is dan vervang je de `IfClause` door de body van de `ElseClause`. Als er geen `ElseClause` is bij een negatieve conditie dan verwijder je de `IfClause` volledig uit de AST. | Must | 10     | Behaald |

# Generator

| ID   | Omschrijving                                                                                                        | Prio | Punten | Status  |
|------|---------------------------------------------------------------------------------------------------------------------|------|--------|---------|
| GE01 | Implementeer de generator in `nl.han.ica.icss.generator.Generator` die de AST naar een CSS2-compliant string omzet. | Must | 5      |         |
| GE02 | Zorg dat de CSS met twee spaties inspringing per scopeniveau gegenereerd wordt.                                     | Must | 5      | Behaald |

# Eigen Implementatie

| ID   | Omschrijving                                                                                                                                                                                                                                                                                                                      | Prio  | Punten | Status  |
|------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------|--------|---------|
| EI01 | Het implementeren van een switch-case functionaliteit, hierbij moeten de types van de cases overeenkomen met het type van de condition in de `switch`. Indien een condition overeenkomt met een case dan moet deze de items vanuit deze case bevatten. Indien dit niet zo is kan hij terugvallen op een (optionele) default case. | Could |        | Behaald |
|      |                                                                                                                                                                                                                                                                                                                                   |       |        |         |