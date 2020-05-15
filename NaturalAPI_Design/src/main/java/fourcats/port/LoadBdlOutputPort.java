package fourcats.port;

import fourcats.entities.Bdl;

public interface LoadBdlOutputPort {
    void showBDLOutput(Bdl b, boolean isBdlLoaded);
}
