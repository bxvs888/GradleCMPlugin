package org.kercheval.gradle.vcs;

import java.util.HashSet;
import java.util.Set;

//
// This class is really a light facade over the API defined in
// org.eclipse.jgit.api.Status. Not all VCS implementations
// are capable of defining things to this level.
//
public class VCSStatus
{

	//
	// This variable includes all files added to revision control
	// (staged) but not committed.
	//
	private Set<String> added = new HashSet<String>();

	//
	// This represents the set of files that have been modified
	// and staged but no committed.
	//
	private Set<String> changed = new HashSet<String>();

	//
	// This represents the set of files that are in conflict (they are
	// modified and staged but need resolution).
	//
	private Set<String> conflicting = new HashSet<String>();

	//
	// This represents files that are no longer on the local
	// workspace, but have been removed in VCS (or staged).
	//
	private Set<String> missing = new HashSet<String>();

	//
	// This represents files that have been modified on the local
	// work space but have not been staged.
	//
	private Set<String> modified = new HashSet<String>();

	//
	// This represents files that have been removed from the workspace
	// and have also been staged (the VCS system knows they are to be deleted).
	//
	private Set<String> removed = new HashSet<String>();

	//
	// This represents new files in the workspace that have not been
	// staged for commit.
	//
	private Set<String> untracked = new HashSet<String>();

	public Set<String> getAdded()
	{
		return added;
	}

	public void setAdded(final Set<String> added)
	{
		this.added = added;
	}

	public Set<String> getChanged()
	{
		return changed;
	}

	public void setChanged(final Set<String> changed)
	{
		this.changed = changed;
	}

	public Set<String> getConflicting()
	{
		return conflicting;
	}

	public void setConflicting(final Set<String> conflicting)
	{
		this.conflicting = conflicting;
	}

	public Set<String> getMissing()
	{
		return missing;
	}

	public void setMissing(final Set<String> missing)
	{
		this.missing = missing;
	}

	public Set<String> getModified()
	{
		return modified;
	}

	public void setModified(final Set<String> modified)
	{
		this.modified = modified;
	}

	public Set<String> getRemoved()
	{
		return removed;
	}

	public void setRemoved(final Set<String> removed)
	{
		this.removed = removed;
	}

	public Set<String> getUntracked()
	{
		return untracked;
	}

	public void setUntracked(final Set<String> untracked)
	{
		this.untracked = untracked;
	}

	public boolean isClean()
	{
		return (getAdded().isEmpty() && getChanged().isEmpty() && getConflicting().isEmpty()
			&& getMissing().isEmpty() && getModified().isEmpty() && getRemoved().isEmpty() && getUntracked()
			.isEmpty());
	}
}
