package com.app.aro.FindKhana;


/**
 * Created by ainhoa on 31/01/18.
 */

public class Pista{

    private String _id;
    private String nombre;
    private String texto;
    private Localizacion localizacion;
    private String respuesta;

    public Pista(int i){
        switch(i){
            case 0:
                _id = "Cerca del árbol más longevo, encontramos una prenda de ropa";
                nombre = "Esta es la pista "+i;
                texto = "La prenda de ropa era una blusa de la victima, estaba empapada en sangre.";
                localizacion = new Localizacion();
                respuesta = null;
                break;
            case 1:
                _id = "En la plaza más cercana, hay un kiosko. Allí hay huellas mojadas";
                nombre = "Esta es la pista "+i;
                texto = "No hace falta ";
                localizacion = new Localizacion();
                respuesta = null;
                break;
            case 2:
                _id = "Las huellas apuntan en dirección al instituto más antiguo de la ciudad";
                nombre = "Esta es la pista "+i;
                texto = "No hace falta ";
                localizacion = new Localizacion();
                respuesta = null;
                break;
            case 3:
                _id = "Parece que le gustan los animales, dirigete a algún sitio relacionado...";
                nombre = "Esta es la pista "+i;
                texto = "No hace falta ";
                localizacion = new Localizacion();
                respuesta = null;
                break;
            case 4:
                _id = "Un charco de sangre! Encuentra al asesino, no debe andar muy lejos, ha seguido el camino al norte"+i;
                nombre = "Esta es la pista "+i;
                texto = "No hace falta ";
                localizacion = new Localizacion();
                respuesta = null;
                break;
            default:
                _id = "Pista "+i;
                nombre = "Esta es la pista "+i;
                texto = "No hace falta ";
                localizacion = new Localizacion();
                respuesta = null;
                break;

        }

    }

    public String getID(){ return this._id; }

    public String getNombre(){ return this.nombre; }

    public String getTexto(){ return this.texto; }

    public Localizacion getLocalizacion(){ return this.localizacion; }

    public String getRespuesta(){ return this.respuesta; }


}
