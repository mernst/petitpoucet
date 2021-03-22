package ca.uqac.lif.petitpoucet.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ca.uqac.lif.petitpoucet.LabeledEdge;
import ca.uqac.lif.petitpoucet.LabeledEdge.Quality;
import ca.uqac.lif.petitpoucet.TraceabilityNode;

public abstract class ConcreteTraceabilityNode implements TraceabilityNode
{
	protected List<LabeledEdge> m_children;

	/**
	 * A counter giving unique IDs to each node
	 */
	protected static int s_idCounter = 0;

	/**
	 * The node's unique ID
	 */
	protected int m_id;

	public ConcreteTraceabilityNode()
	{
		super();
		m_id = s_idCounter++;
		m_children = new ArrayList<LabeledEdge>();
	}

	public int getId()
	{
		return m_id;
	}

	@Override
	public void addChild(TraceabilityNode n, Quality q)
	{
		addChild(n, q, false);
	}

	public void addChild(TraceabilityNode n, Quality q, boolean check_duplicates)
	{
		if (n == this)
		{
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, "Attempting to connect a node to itself");
		}
		LabeledEdge le = new ConcreteLabeledEdge(n, q);
		if (check_duplicates)
		{
			for (LabeledEdge c_le : m_children)
			{
				if (n.equals(c_le.getNode()))
				{
					break;
				}
			}
			m_children.add(le);
		}
		else
		{
			m_children.add(le);
		}
	}

	@Override
	public void addChild(LabeledEdge e)
	{
		m_children.add(e);
	}

	@Override
	public List<LabeledEdge> getChildren()
	{
		return m_children;
	}

	@Override
	public int hashCode()
	{
		return m_id;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == null || !(o instanceof ConcreteTraceabilityNode))
		{
			return false;
		}
		return m_id == ((ConcreteTraceabilityNode) o).m_id;
	}

	protected String toString(String indent)
	{
		return indent;
	}
}
