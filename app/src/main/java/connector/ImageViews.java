package connector;


import android.graphics.Bitmap;

public class ImageViews {

    private Bitmap image;

    public long	idSolicitud;
    public long Color_Switch;

    public Boolean	Resuelto;
    public String Respuesa;
    public String Messenger;
    public String Instagram;
    public String Snapchat;
    public String Facebook;
    public String Fit_Girls_Guide;
    public String Trump_Dump;
    public String title;

    public ImageViews(Bitmap bitmap, String s) {
    }

    public long getidSolicitud() {
        return idSolicitud;
    }

    public String getRespuesa() {
        return Respuesa;
    }

    public void setRespuesa(String Respuesa) {
        this.Respuesa = Respuesa;
    }


    public long Color_Switch() {
        return Color_Switch;
    }

    public String Messenger() {

        return Messenger;
    }

    public String Instagram() {
        return Instagram;
    }

    public String Snapchat() {

        return Snapchat;
    }

    public String Facebook() {

        return Facebook;
    }

    public String Fit_Girls_Guide() {

        return Fit_Girls_Guide;
    }

    public String Trump_Dump() {

        return Trump_Dump;
    }

    public String getTitle() {
        return title;
    }

    public Bitmap getImage() {
        return image;
    }

}

