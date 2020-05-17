
declare var window: any;

export class DynamicEnvironment{
    public get environnemt(){
        return window.config && window.config.environnemt;
    }
    public get keycloak(){
        return window.config && window.config.keycloak;
    }

}