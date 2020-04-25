package FourCats.UseCaseInteractor;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.RepositoryAccess;
import FourCats.Port.CreateBdlOutputPort;
import FourCats.Port.ViewBdlInputPort;
import FourCats.Port.ViewBdlOutputPort;
import FourCats.UseCaseUtilities.AnalyzeDocument;

import java.io.IOException;
import java.util.LinkedList;

public class ViewBdl implements ViewBdlInputPort {

    private RepositoryAccess repository;
    private ViewBdlOutputPort output;

    public ViewBdl(RepositoryAccess ra,ViewBdlOutputPort p){
        this.repository = ra;
        this.output = p;
    }

    @Override
    public void view(String nameBdl) {
        Bdl bdl = repository.readBdl(nameBdl);
        output.showViewBdlOutput(bdl);
    }
}
