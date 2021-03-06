package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import play.data.validation.Constraints.*;


@Entity
public class Sessao extends Model {

    @Id
    @Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
    public Date data_hora; /* Tipo simultaneo para data e horario */

    @Required
    public String descricao;

    @Required
    public String ata;

    @Required
    public String carater;

    @Required
    public String presidente;

    @ManyToMany(cascade = CascadeType.REMOVE)
    public List<models.Parlamentar> parlamentares = new ArrayList<models.Parlamentar>();

    //Construtor e metodo create para auxiliar nos testes JUNIT. 
    public Sessao(Date dat, String d, String a, String c, String pe) {
        data_hora = dat;
        descricao = d;
        ata = a;
        carater = c;
        presidente = pe;
        //Adicionando presidente como primeiro parlamentar participante da sessao. 
        //parlamentares.add(models.Parlamentar.find.where().eq("parlamentar.nome", pe));
    }
    
    public static Sessao create(Date dat, String d, String a, String c, String pe) {
        Sessao s = new Sessao(dat, d, a, c, pe);
        s.save();
        s.saveManyToManyAssociations("parlamentares");
        return s;
    }
    

    public static Finder<Date,Sessao> find = new Finder<Date,Sessao>(Date.class, Sessao.class);

    public List<Sessao> findPresent(Long pa) {
        return find.where().eq("parlamentares.id", pa).findList();
    }

}