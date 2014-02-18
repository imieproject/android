/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test.fixture;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.command.OrmCommand;
import com.tactfactory.harmony.command.ProjectCommand;
import com.tactfactory.harmony.fixture.command.FixtureCommand;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.test.CommonTest;
import com.tactfactory.harmony.utils.TactFileUtils;

/**
 * Test class for Fixtures generation and loading.
 */
public class FixtureGlobalTest extends CommonTest {
	/** Fixture path. */
	private static final String FIXTURE_PATH =
			"android/src/com/tactfactory/harmony/test/demact/fixture/";

	/**
	 * @throws Exception if something bad happened.
	 */
	@BeforeClass
	public static void setUpBefore() throws Exception {
		CommonTest.setUpBefore();
		initAll();

		final File dirfixtures = new File("fixtures/");
		TactFileUtils.deleteRecursive(dirfixtures);
	}

	@Before
	@Override
	public final void setUp() throws Exception {
		super.setUp();
	}

	@After
	@Override
	public final void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Initialize the tests.
	 */
	private static void initAll() {
		System.out.println("\nTest Orm generate entity");
		System.out.println("######################################"
				+ "#########################################");

		getHarmony().findAndExecute(ProjectCommand.INIT_ANDROID, null, null);
		makeEntities();
		getHarmony().findAndExecute(
				OrmCommand.GENERATE_ENTITIES, new String[] {}, null);
		getHarmony().findAndExecute(
				OrmCommand.GENERATE_CRUD, new String[] {}, null);
		getHarmony().findAndExecute(
				FixtureCommand.FIXTURE_INIT,
				new String[] {"--format=xml", "--force=true"},
				null);
	}

	/**
	 * Tests if loaders are generated.
	 */
	@Test
	public final void hasFixtureLoaders() {
		CommonTest.hasFindFile(FIXTURE_PATH + "UserDataLoader.java");
		CommonTest.hasFindFile(FIXTURE_PATH + "CommentDataLoader.java");
		CommonTest.hasFindFile(FIXTURE_PATH + "PostDataLoader.java");
		CommonTest.hasFindFile(FIXTURE_PATH + "ViewComponentDataLoader.java");
		CommonTest.hasFindFile(FIXTURE_PATH + "FixtureBase.java");
		CommonTest.hasFindFile(FIXTURE_PATH + "DataManager.java");
	}

	/**
	 * Tests if XML Fixtures have really been loaded.
	 */
	@Test
	public final void hasFixturesXml() {
		// Copy fixture files
		copyFixturesXml();
		CommonTest.getHarmony().findAndExecute(
				FixtureCommand.FIXTURE_LOAD, new String[] {}, null);

		CommonTest.hasFindFile("android/assets/app/User.xml");
		CommonTest.hasFindFile("android/assets/app/Comment.xml");
		CommonTest.hasFindFile("android/assets/app/Post.xml");
		CommonTest.hasFindFile("android/assets/app/ViewComponent.xml");

		CommonTest.hasFindFile("android/assets/test/User.xml");
		CommonTest.hasFindFile("android/assets/test/Comment.xml");
		CommonTest.hasFindFile("android/assets/test/Post.xml");
		CommonTest.hasFindFile("android/assets/test/ViewComponent.xml");
	}

	/**
	 * Tests if YML Fixtures have really been loaded.
	 */
	@Test
	public final void hasFixturesYml() {
		//Purge & init
		CommonTest.getHarmony().findAndExecute(
				FixtureCommand.FIXTURE_PURGE, new String[] {}, null);
		CommonTest.getHarmony().findAndExecute(
				FixtureCommand.FIXTURE_INIT,
				new String[] {"--format=yml", "--force=true"},
				null);

		// Copy fixture files
		copyFixturesYml();
		CommonTest.getHarmony().findAndExecute(
				FixtureCommand.FIXTURE_LOAD, new String[] {}, null);

		CommonTest.hasFindFile("android/assets/app/User.yml");
		CommonTest.hasFindFile("android/assets/app/Comment.yml");
		CommonTest.hasFindFile("android/assets/app/Post.yml");
		CommonTest.hasFindFile("android/assets/app/ViewComponent.yml");

		CommonTest.hasFindFile("android/assets/test/User.yml");
		CommonTest.hasFindFile("android/assets/test/Comment.yml");
		CommonTest.hasFindFile("android/assets/test/Post.yml");
		CommonTest.hasFindFile("android/assets/test/ViewComponent.yml");
	}

	/**
	 * Copy XML fixtures in test project.
	 */
	protected static final void copyFixturesXml() {
		final String pathNameSpace =
				ApplicationMetadata.INSTANCE.getProjectNameSpace().replaceAll(
						"\\.", "/");
		
		String srcDir = 
				String.format("%s/tact-core/resources/%s/%s/%s/",
						Harmony.getBundlePath(),
						pathNameSpace, 
						"fixture",
						"xml");

		final String destDir = String.format("fixtures/");

		// FileUtils.copyDirectory(new File(srcDir), new File(destDir));
		TactFileUtils.makeFolderRecursive(srcDir, destDir, true);
		//destDir = String.format("fixtures/test/");
		//TactFileUtils.makeFolderRecursive(srcDir, destDir, true);
	}

	/**
	 * Copy YML fixtures in test project.
	 */
	protected static final void copyFixturesYml() {
		final String pathNameSpace =
				ApplicationMetadata.INSTANCE.getProjectNameSpace().replaceAll(
						"\\.", "/");
		
		String srcDir = 
				String.format("%s/tact-core/resources/%s/%s/%s/",
						Harmony.getBundlePath(),
						pathNameSpace, 
						"fixture",
						"yml");

		final String destDir = String.format("fixtures/");

		// FileUtils.copyDirectory(new File(srcDir), new File(destDir));
		TactFileUtils.makeFolderRecursive(srcDir, destDir, true);
	}
}
