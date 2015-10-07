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

package net.imagej.ops.filter.fft;

import net.imagej.ops.AbstractFunctionOp;
import net.imagej.ops.AbstractHybridOp;
import net.imagej.ops.Ops;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.ImgFactory;
import net.imglib2.outofbounds.OutOfBoundsFactory;
import net.imglib2.type.Type;
import net.imglib2.type.numeric.ComplexType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.complex.ComplexFloatType;

import org.scijava.Priority;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * Forward FFT function that operates on RAI
 * 
 * @author Brian Northan
 * @param <T>
 * @param <I>
 */
@Plugin(type = Ops.Filter.FFT.class, name = Ops.Filter.FFT.NAME,
	priority = Priority.HIGH_PRIORITY)
public class FFTFunctionOp<T extends RealType<T>, I extends RandomAccessibleInterval<T>, C extends ComplexType<C>, O extends RandomAccessibleInterval<C>>
	extends AbstractFunctionOp<I, O>
{

	/**
	 * The size of border to apply in each dimension
	 */
	@Parameter(required = false)
	private long[] borderSize = null;

	/**
	 * Whether to perform a fast FFT. If true the input will be extended to the
	 * next fast FFT size. If false the input will be computed using the original
	 * input dimensions (if possible). If the input dimensions are not supported
	 * by the underlying FFT implementation the input will be extended to the
	 * nearest size that is supported.
	 */
	@Parameter(required = false)
	private Boolean fast = true;

	/**
	 * The OutOfBoundsFactory used to extend the image
	 */
	@Parameter(required = false)
	private OutOfBoundsFactory<T, RandomAccessibleInterval<T>> obf;

	/**
	 * The ImgFactory used to create the output
	 */
	@Parameter(required = false)
	private ImgFactory factory;

	/**
	 * The type of the output
	 */
	@Parameter(required = false)
	private Type<C> fftType;

	private long[] paddedSize;

	private long[] fftSize;

	/**
	 * create the output based on the input. If fast=true the size is determined
	 * such that the underlying FFT implementation will run as fast as possible.
	 * If fast=false the size is determined such that the underlying FFT
	 * implementation will use the smallest amount of memory possible.
	 */
	public O createOutput(I input) {

		long[] inputSize = new long[input.numDimensions()];

		for (int d = 0; d < input.numDimensions(); d++) {
			inputSize[d] = input.dimension(d);

			if (borderSize != null) {
				inputSize[d] += borderSize[d];
			}
		}

		paddedSize = new long[inputSize.length];
		fftSize = new long[inputSize.length];

		ops().filter().fftSize(inputSize, paddedSize, fftSize, true, fast);

		if (fftType == null) {
			fftType = (C) new ComplexFloatType();
		}

		if (factory == null) {
			factory = (ImgFactory) ops().create().imgFactory(fftSize, fftType);
		}

		return (O) ops().create().img(fftSize, fftType);

	}

	@Override
	public O compute(final I input) {

		O output = createOutput(input);

		ops().filter().fft(output, input, obf, paddedSize);

		return output;
	}
}
