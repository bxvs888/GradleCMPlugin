package org.kercheval.gradle.buildrelease;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Ref;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskExecutionException;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.kercheval.gradle.util.GradleUtil;
import org.kercheval.gradle.vcs.JGitTestRepository;

public class BuildReleaseMergeTaskTest
{
	@Test
	public void testMerge()
		throws InvalidRemoteException, TransportException, IOException, GitAPIException
	{
		final JGitTestRepository repoUtil = new JGitTestRepository();
		try
		{
			final Project project = ProjectBuilder.builder()
				.withProjectDir(repoUtil.getStandardFile()).build();
			final GradleUtil gradleUtil = new GradleUtil(project);

			project.apply(new LinkedHashMap<String, String>()
			{
				{
					put("plugin", "buildrelease");
				}
			});
			final BuildReleaseInitTask initTask = (BuildReleaseInitTask) gradleUtil
				.getTask("buildreleaseinit");
			final BuildReleaseMergeTask mergeTask = (BuildReleaseMergeTask) gradleUtil
				.getTask("buildreleasemerge");

			initTask.setIgnoreorigin(true);

			try
			{
				mergeTask.doTask();
				Assert.fail("Exception expected");
			}
			catch (final TaskExecutionException e)
			{
				// Expected
			}

			initTask.setReleasebranch("master");
			initTask.setMainlinebranch("OriginBranch1");
			initTask.setRemoteorigin("myOrigin");

			mergeTask.doTask();

			initTask.setIgnoreorigin(false);

			final Ref originHead = repoUtil.getOriginRepo().getRef("refs/heads/master");
			Ref localHead = repoUtil.getStandardRepo().getRef("refs/heads/master");
			Assert.assertNotEquals(localHead.getObjectId().getName(), originHead.getObjectId()
				.getName());

			mergeTask.doTask();

			localHead = repoUtil.getStandardRepo().getRef("refs/heads/master");
			Assert.assertEquals(localHead.getObjectId().getName(), originHead.getObjectId()
				.getName());

			final File newFile = new File(repoUtil.getStandardFile().getAbsolutePath()
				+ "/NotCleanFile.txt");
			repoUtil.writeRandomContentFile(newFile);

			try
			{
				mergeTask.doTask();
				Assert.fail("Expected Exception");
			}
			catch (final TaskExecutionException e)
			{
				// Expected
			}

		}
		finally
		{
			repoUtil.close();
		}
	}
}