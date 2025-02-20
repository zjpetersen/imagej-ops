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
package net.imagej.ops.geometric3d;

import net.imagej.ops.AbstractFunctionOp;
import net.imagej.ops.Contingent;
import net.imagej.ops.FunctionOp;
import net.imagej.ops.Op;
import net.imagej.ops.Ops.Geometric3D;
import net.imglib2.roi.IterableRegion;
import net.imglib2.type.BooleanType;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.Priority;
import org.scijava.plugin.Plugin;

/**
 * Generic implementation of {@link net.imagej.ops.Ops.Geometric3D.Compactness}.
 * 
 * @author Tim-Oliver Buchholz, University of Konstanz.
 */
@Plugin(type = Op.class, name = Geometric3D.Compactness.NAME, label = "Geometric3D: Compactness", priority = Priority.VERY_HIGH_PRIORITY)
public class DefaultCompactnessFeature<B extends BooleanType<B>>
		extends
			AbstractFunctionOp<IterableRegion<B>, DoubleType>
		implements
			Geometric3DOp<IterableRegion<B>, DoubleType>,
			Geometric3D.Compactness,
			Contingent {

	private FunctionOp<IterableRegion<B>, DoubleType> surfacePixel;
	
	private FunctionOp<IterableRegion<B>, DoubleType> volume;
	
	@Override
	public void initialize() {
		surfacePixel = ops().function(DefaultSurfacePixelFeature.class, DoubleType.class, in());
		volume = ops().function(DefaultVolumeFeature.class, DoubleType.class, in());
	}
	
	@Override
	public DoubleType compute(final IterableRegion<B> input) {
		double s3 = Math.pow(surfacePixel.compute(input).get(), 3);
		double v2 = Math.pow(volume.compute(input).get(), 2);

		return new DoubleType((v2 * 36.0 * Math.PI) / s3);
	}

	@Override
	public boolean conforms() {
		return in().numDimensions() == 3;
	}

}
