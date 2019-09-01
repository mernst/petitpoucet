/*
    Petit Poucet, a library for tracking links between objects.
    Copyright (C) 2016-2019 Sylvain Hallé

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ca.uqac.lif.petitpoucet.functions.strings;

import java.util.List;

import ca.uqac.lif.petitpoucet.ComposedDesignator;
import ca.uqac.lif.petitpoucet.Designator;
import ca.uqac.lif.petitpoucet.TraceabilityNode;
import ca.uqac.lif.petitpoucet.TraceabilityQuery;
import ca.uqac.lif.petitpoucet.Tracer;
import ca.uqac.lif.petitpoucet.LabeledEdge.Quality;
import ca.uqac.lif.petitpoucet.circuit.CircuitDesignator.NthInput;
import ca.uqac.lif.petitpoucet.common.StringDesignator;
import ca.uqac.lif.petitpoucet.functions.NaryFunction;

public class Substring extends NaryFunction
{

	/**
	 * The start index of the substring
	 */
	protected int m_startIndex;
	
	/**
	 * The end index of the substring
	 */
	protected int m_endIndex;
	
	/**
	 * The length of the last evaluated string
	 */
	protected int m_length;
	
	/**
	 * Creates a new instance of the substring function.
	 * @param start The start index of the substring
	 * @param end The end index of the substring
	 */
	public Substring(int start, int end)
	{
		super(1);
		m_startIndex = start;
		m_endIndex = end;
		m_length = 0;
	}
		
	@Override
	public void getValue(Object[] inputs, Object[] outputs)
	{
		String s = inputs[0].toString();
		m_length = s.length();
		int start = Math.min(m_startIndex, m_length);
		int end = Math.min(m_endIndex, m_length);
		outputs[0] = s.substring(start, end);
	}
	
	@Override
	protected void answerQuery(TraceabilityQuery q, int output_nb, Designator d,
			TraceabilityNode root, Tracer factory, List<TraceabilityNode> leaves)
	{
		Designator top = d.peek();
		Designator tail = d.tail();
		if (tail == null)
		{
			tail = Designator.identity;
		}
		if (!m_evaluated)
		{
			// We did not evaluate the function; the best we can say is that the output depends on
			// the whole input string, but this is an over-approximation
			ComposedDesignator cd = new ComposedDesignator(tail, new NthInput(0));
			TraceabilityNode child = factory.getObjectNode(cd, this);
			root.addChild(child, Quality.OVER);
			leaves.add(child);
			return;
		}
		if (top instanceof StringDesignator.Range)
		{
			StringDesignator.Range sdr = (StringDesignator.Range) top;
			int offset = Math.min(m_startIndex, m_length);
			int len = Math.min(sdr.getLength(), m_length);
			int start = sdr.getStartIndex() + offset;
			int end = offset + len;
			ComposedDesignator cd = new ComposedDesignator(tail, new StringDesignator.Range(start, end), new NthInput(0));
			TraceabilityNode child = factory.getObjectNode(cd, this);
			root.addChild(child, Quality.OVER);
			leaves.add(child);
			return;
		}
		if (top instanceof Designator.Identity)
		{
			int start = Math.min(m_startIndex, m_length);
			int end = Math.min(m_endIndex, m_length);
			ComposedDesignator cd = new ComposedDesignator(tail, new StringDesignator.Range(start, end), new NthInput(0));
			TraceabilityNode child = factory.getObjectNode(cd, this);
			root.addChild(child, Quality.OVER);
			leaves.add(child);
			return;
		}
	}
	
	@Override
	public String toString()
	{
		return "Subtring " + m_startIndex + "-" + m_endIndex;
	}
}
