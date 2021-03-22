package corewar.Local;

// Direct : " " | Immédiat : "#" | Indirect : "@"
public enum Mode {
    DIRECT{ public String toString() { return "" ; } },
    IMMEDIAT{ public String toString() { return "#" ; } }, 
    INDIRECT{ public String toString() { return "@" ; } };
}
