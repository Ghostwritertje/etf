package be.ghostwritertje;

import be.ghostwritertje.wicket.HomePage;
import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import org.apache.log4j.Logger;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public class WicketApplication extends WebApplication {
    private static Logger logger = Logger.getLogger(WicketApplication.class);

    @Override
    public Class<? extends Page> getHomePage() {
        logger.info("Homepage wordt opgevraagd!");
        return HomePage.class;
    }

    @Override
    protected void init() {
        super.init();
        super.getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        this.configureBootstrap();

        WicketAuthorizationStrategy wicketAuthorizationStrategy = new WicketAuthorizationStrategy();

        this.getSecuritySettings().
                setAuthorizationStrategy(wicketAuthorizationStrategy);
        this.getSecuritySettings().setUnauthorizedComponentInstantiationListener(wicketAuthorizationStrategy);
    }
       @Override
    public Session newSession(Request request, Response response) {
        return new WicketSession(request);
    }

    private void configureBootstrap() {
        final IBootstrapSettings settings = new BootstrapSettings();
 /*       final ThemeProvider themeProvider = new BootswatchThemeProvider(BootswatchTheme.Superhero);

        settings.setJsResourceFilterName("footer-container")
                .setThemeProvider(themeProvider);
*/
        Bootstrap.install(this, settings);

    }

}
