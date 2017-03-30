package arquillian;


import fr.unice.polytech.isa.tcf.entities.Cookies;
import fr.unice.polytech.isa.tcf.entities.Customer;
import fr.unice.polytech.isa.tcf.exceptions.AlreadyExistingCustomerException;
import fr.unice.polytech.isa.tcf.interceptors.LogParameters;


import fr.unice.polytech.isa.tcf.utils.Database;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import javax.ejb.EJB;
import java.io.File;

public abstract class AbstractTCFTest {


	@EJB
	protected Database memory;

	@Deployment
	public static JavaArchive createDeployment() {
		// Building a Web ARchive (WAR) containing the following elements:
		return ShrinkWrap.create(JavaArchive.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				// Utils
				.addPackage(Database.class.getPackage())
				// Entities
				.addPackage(Cookies.class.getPackage())
				// Interceptors
				.addPackage(LogParameters.class.getPackage())
				// Exceptions
				.addPackage(AlreadyExistingCustomerException.class.getPackage());
				}

}
