package fourcats.entity;

public class PLA {

    private String extension;
    private String text;

    private String customClass;
    private String customBody;

    public PLA(String t){
        String[] split = t.split("\n",2);
        extension = split[0];
        text = split[1];

        String[] splitCustom = text.split("\ncustom class\n");
        text = splitCustom[0];
        customClass = splitCustom[1];

        customBody = "";
        String[] splitCustomBody = customClass.split("\n");
        for(int i = 1; i < splitCustomBody.length-1; i++){
            customBody = customBody.concat(splitCustomBody[i]);
            customBody = customBody.concat("\n");
        }
    }

    public String getText() {
        return text;
    }

    public String getExtension() {
        return extension;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCustomClass() {
        return customClass;
    }

    public String getCustomBody() {
        return customBody;
    }

}
