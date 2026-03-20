package nl.han.ica.icss.ast;

// Scoped is a interface to help figure out if an block should get a new scope or not.
// e.g.
/*
....
if[TRUE] {
    VARIABLENAME := #ffffff;
    color: VARIABLENAME;
}

background-color: VARIABLENAME <-- results in a undefined variable error
....
 */
public interface Scoped {
}
