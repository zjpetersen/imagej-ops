/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2014 - 2015 Board of Regents of the University of
 * Wisconsin-Madison, University of Konstanz and Brian Northan.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package net.imagej.ops.features.haralick;

import net.imagej.ops.Ops.Haralick;
import net.imagej.ops.Ops.Haralick.TextureHomogeneity;
import net.imglib2.IterableInterval;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.plugin.Plugin;

/**
 * Implementation of Texture Homogeneity Haralick Feature
 * 
 * @author Andreas Grauman, University of Konstanz
 * @author Christian Dietz, University of Konstanz
 */
@Plugin(type = HaralickFeature.class,
	label = "Haralick: Texture Homogeneity Feature",
	name = Haralick.TextureHomogeneity.NAME)
public class DefaultTextureHomogeneity<T extends RealType<T>> extends
	AbstractHaralickFeature<T>implements TextureHomogeneity
{

	@Override
	public void compute(final IterableInterval<T> input,
		final DoubleType output)
	{
		final double[][] matrix = getCooccurrenceMatrix(input);

		final double nrGreyLevel = matrix.length;

		double res = 0;
		for (int i = 0; i < nrGreyLevel; i++) {
			for (int j = 0; j < nrGreyLevel; j++) {
				res += matrix[i][j] / (1 + Math.abs(i - j));
			}
		}

		output.set(res);
	}
}
