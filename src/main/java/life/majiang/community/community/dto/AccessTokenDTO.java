package life.majiang.community.community.dto;

public class AccessTokenDTO {
    private String client_id;
    private String aclient_secret;
    private String acode;
    private String redirect_uri;

    public String getAclient_secret() {
        return aclient_secret;
    }

    public void setAclient_secret(String aclient_secret) {
        this.aclient_secret = aclient_secret;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getAcode() {
        return acode;
    }

    public void setAcode(String acode) {
        this.acode = acode;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
