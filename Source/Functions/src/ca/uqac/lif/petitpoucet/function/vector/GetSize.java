/*
    Petit Poucet, a library for tracking links between objects.
    Copyright (C) 2016-2021 Sylvain Hallé

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
package ca.uqac.lif.petitpoucet.function.vector;

import java.util.List;

/**
 * Calculates the size of the input vector.
 * 
 * @author Sylvain Hallé
 */
public class GetSize extends VectorFunction
{
	/**
	 * Creates a new instance of the function.
	 */
	public GetSize()
	{
		super(1);
	}
	
	@Override
	protected Number getOutputValue(List<?> ... in_lists)
	{
		return in_lists[0].size();
	}
	
	@Override
	public String toString()
	{
		return "Size";
	}
	
	@Override
	public GetSize duplicate(boolean with_state)
	{
		GetSize vs = new GetSize();
		copyInto(vs, with_state);
		return vs;
	}
}
