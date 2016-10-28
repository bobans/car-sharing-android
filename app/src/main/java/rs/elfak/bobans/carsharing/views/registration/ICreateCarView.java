package rs.elfak.bobans.carsharing.views.registration;

import java.util.List;

import rs.elfak.bobans.carsharing.models.Make;
import rs.elfak.bobans.carsharing.models.Model;
import rs.elfak.bobans.carsharing.views.IBaseView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public interface ICreateCarView extends IBaseView<Object> {
    void setMakes(List<Make> makes);
    void setModels(List<Model> models);
}
