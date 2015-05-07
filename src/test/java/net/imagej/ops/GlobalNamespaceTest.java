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

package net.imagej.ops;

import net.imagej.ops.Ops.ASCII;
import net.imagej.ops.Ops.CreateImg;
import net.imagej.ops.Ops.Equation;
import net.imagej.ops.Ops.Eval;
import net.imagej.ops.Ops.FFT;

import org.junit.Test;

/**
 * Tests that the ops of the global namespace have corresponding type-safe Java
 * method signatures declared in the {@link OpService} class.
 *
 * @author Curtis Rueden
 */
public class GlobalNamespaceTest extends AbstractNamespaceTest {

	/** Tests for {@link CreateImg} method convergence. */
	@Test
	public void testCreateImg() {
		assertComplete(null, OpService.class, CreateImg.NAME);
	}

	/** Tests for {@link ASCII} method convergence. */
	@Test
	public void testASCII() {
		assertComplete(null, OpService.class, ASCII.NAME);
	}

	/** Tests for {@link Equation} method convergence. */
	@Test
	public void testEquation() {
		assertComplete(null, OpService.class, Equation.NAME);
	}

	/** Tests for {@link Eval} method convergence. */
	@Test
	public void testEval() {
		assertComplete(null, OpService.class, Eval.NAME);
	}

	/** Tests for {@link FFT} method convergence. */
	@Test
	public void testFFT() {
		assertComplete(null, OpService.class, FFT.NAME);
	}
}
