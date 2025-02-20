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

package net.imagej.ops.imagemoments;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import net.imagej.ops.AbstractOpTest;
import net.imagej.ops.Ops.ImageMoments;
import net.imagej.ops.Ops.ImageMoments.CentralMoment02;
import net.imagej.ops.Ops.ImageMoments.CentralMoment03;
import net.imagej.ops.Ops.ImageMoments.CentralMoment11;
import net.imagej.ops.Ops.ImageMoments.CentralMoment12;
import net.imagej.ops.Ops.ImageMoments.CentralMoment20;
import net.imagej.ops.Ops.ImageMoments.CentralMoment21;
import net.imagej.ops.Ops.ImageMoments.CentralMoment30;
import net.imagej.ops.Ops.ImageMoments.HuMoment1;
import net.imagej.ops.Ops.ImageMoments.HuMoment2;
import net.imagej.ops.Ops.ImageMoments.HuMoment3;
import net.imagej.ops.Ops.ImageMoments.HuMoment4;
import net.imagej.ops.Ops.ImageMoments.HuMoment5;
import net.imagej.ops.Ops.ImageMoments.HuMoment6;
import net.imagej.ops.Ops.ImageMoments.HuMoment7;
import net.imagej.ops.Ops.ImageMoments.Moment00;
import net.imagej.ops.Ops.ImageMoments.Moment01;
import net.imagej.ops.Ops.ImageMoments.Moment10;
import net.imagej.ops.Ops.ImageMoments.Moment11;
import net.imagej.ops.Ops.ImageMoments.NormalizedCentralMoment02;
import net.imagej.ops.Ops.ImageMoments.NormalizedCentralMoment03;
import net.imagej.ops.Ops.ImageMoments.NormalizedCentralMoment11;
import net.imagej.ops.Ops.ImageMoments.NormalizedCentralMoment12;
import net.imagej.ops.Ops.ImageMoments.NormalizedCentralMoment20;
import net.imagej.ops.Ops.ImageMoments.NormalizedCentralMoment21;
import net.imagej.ops.Ops.ImageMoments.NormalizedCentralMoment30;
import net.imglib2.Cursor;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.integer.UnsignedByteType;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests {@link ImageMoments}.
 * 
 * @author Daniel Seebacher
 */
public class ImageMomentsTest extends AbstractOpTest {

	private static Img<UnsignedByteType> img;

	@BeforeClass
	public static void createImg() {

		Img<UnsignedByteType> tmp =
			ArrayImgs.unsignedBytes(new long[] { 100, 100 });

		Random rand = new Random(1234567890L);
		final Cursor<UnsignedByteType> cursor = tmp.cursor();
		while (cursor.hasNext()) {
			cursor.next().set(rand.nextInt((int) tmp.firstElement().getMaxValue()));
		}

		img = tmp;
	}

	/**
	 * Test the Moment Ops.
	 */
	@Test
	public void testMoments() {

		assertEquals(Moment00.NAME, 1277534.0, ops.imagemoments().moment00(img)
			.getRealDouble(), 1e-3);
		assertEquals(Moment10.NAME, 6.3018047E7, ops.imagemoments().moment10(img)
			.getRealDouble(), 1e-3);
		assertEquals(Moment01.NAME, 6.3535172E7, ops.imagemoments().moment01(img)
			.getRealDouble(), 1e-3);
		assertEquals(Moment11.NAME, 3.12877962E9, ops.imagemoments().moment11(img)
			.getRealDouble(), 1e-3);
	}

	/**
	 * Test the Central Moment Ops.
	 */
	@Test
	public void testCentralMoments() {
		assertEquals(CentralMoment11.NAME, -5275876.956702232, ops.imagemoments()
			.centralMoment11(img).getRealDouble(), 1e-3);
		assertEquals(CentralMoment02.NAME, 1.0694469880269928E9, ops.imagemoments()
			.centralMoment02(img).getRealDouble(), 1e-3);
		assertEquals(CentralMoment20.NAME, 1.0585772432642083E9, ops.imagemoments()
			.centralMoment20(img).getRealDouble(), 1e-3);
		assertEquals(CentralMoment12.NAME, 5478324.271270752, ops.imagemoments()
			.centralMoment12(img).getRealDouble(), 1e-3);
		assertEquals(CentralMoment21.NAME, -2.1636455685491943E8, ops
			.imagemoments().centralMoment21(img).getRealDouble(), 1e-3);
		assertEquals(CentralMoment30.NAME, 1.735560232991333E8, ops.imagemoments()
			.centralMoment30(img).getRealDouble(), 1e-3);
		assertEquals(CentralMoment03.NAME, -4.0994213161157227E8, ops
			.imagemoments().centralMoment03(img).getRealDouble(), 1e-3);
	}

	/**
	 * Test the Normalized Central Moment Ops.
	 */
	@Test
	public void testNormalizedCentralMoments() {
		assertEquals(NormalizedCentralMoment11.NAME, -3.2325832933879204E-6, ops
			.imagemoments().normalizedCentralMoment11(img).getRealDouble(), 1e-3);

		assertEquals(NormalizedCentralMoment02.NAME, 6.552610106398286E-4, ops
			.imagemoments().normalizedCentralMoment02(img).getRealDouble(), 1e-3);

		assertEquals(NormalizedCentralMoment20.NAME, 6.486010078361372E-4, ops
			.imagemoments().normalizedCentralMoment20(img).getRealDouble(), 1e-3);

		assertEquals(NormalizedCentralMoment12.NAME, 2.969727272701925E-9, ops
			.imagemoments().normalizedCentralMoment12(img).getRealDouble(), 1e-3);

		assertEquals(NormalizedCentralMoment21.NAME, -1.1728837022440002E-7, ops
			.imagemoments().normalizedCentralMoment21(img).getRealDouble(), 1e-3);

		assertEquals(NormalizedCentralMoment30.NAME, 9.408242926327751E-8, ops
			.imagemoments().normalizedCentralMoment30(img).getRealDouble(), 1e-3);

		assertEquals(NormalizedCentralMoment03.NAME, -2.22224218245127E-7, ops
			.imagemoments().normalizedCentralMoment03(img).getRealDouble(), 1e-3);
	}

	/**
	 * Test the Hu Moment Ops.
	 */
	@Test
	public void testHuMoments() {
		assertEquals(HuMoment1.NAME, 0.001303862018475966, ops.imagemoments()
			.huMoment1(img).getRealDouble(), 1e-3);
		assertEquals(HuMoment2.NAME, 8.615401633994056e-11, ops.imagemoments()
			.huMoment2(img).getRealDouble(), 1e-3);
		assertEquals(HuMoment3.NAME, 2.406124306990366e-14, ops.imagemoments()
			.huMoment3(img).getRealDouble(), 1e-3);
		assertEquals(HuMoment4.NAME, 1.246879188175627e-13, ops.imagemoments()
			.huMoment4(img).getRealDouble(), 1e-3);
		assertEquals(HuMoment5.NAME, -6.610443880647384e-27, ops.imagemoments()
			.huMoment5(img).getRealDouble(), 1e-3);
		assertEquals(HuMoment6.NAME, 1.131019166855569e-18, ops.imagemoments()
			.huMoment6(img).getRealDouble(), 1e-3);
		assertEquals(HuMoment7.NAME, 1.716256940536518e-27, ops.imagemoments()
			.huMoment7(img).getRealDouble(), 1e-3);
	}

}
