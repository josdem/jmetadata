/*
   Copyright 2013 Jose Luis De la Cruz Morales joseluis.delacruz@gmail.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package org.jas.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jas.model.Metadata;
import org.jas.service.DefaultService;
import org.jas.service.MetadataService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestDefaultService {

	private static final String TOTAL_TRACKS = "2";
	private static final String TRACK_NUMBER_METADATA_ONE = "1";
	private static final String TRACK_NUMBER_METADATA_TWO = TOTAL_TRACKS;
	private static final String CD_NUMBER = "1";
	private static final String TOTAL_CD_NUMBER = "1";

	@InjectMocks
	private DefaultService defaultService = new DefaultService();

	@Mock
	private Metadata metadata_one;
	@Mock
	private Metadata metadata_two;
	@Mock
	private MetadataService metadataService;

	private List<Metadata> metadatas = new ArrayList<Metadata>();

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldCompleteTotalTracks() throws Exception {
		setTracknumberExpectations();
		when(metadataService.isSameAlbum(metadatas)).thenReturn(true);

		assertTrue(defaultService.isCompletable(metadatas));
	}

	@Test
	public void shouldComplete() throws Exception {
		when(metadataService.isSameAlbum(metadatas)).thenReturn(true);
		setTracknumberExpectations();

		defaultService.complete(metadatas);


		verify(metadata_one).setTotalTracks(TOTAL_TRACKS);
		verify(metadata_one).setCdNumber(CD_NUMBER);
		verify(metadata_one).setTotalCds(TOTAL_CD_NUMBER);
		verify(metadata_two).setTotalTracks(TOTAL_TRACKS);
		verify(metadata_two).setCdNumber(CD_NUMBER);
		verify(metadata_two).setTotalCds(TOTAL_CD_NUMBER);
	}

	private void setTracknumberExpectations() {
		when(metadata_one.getTrackNumber()).thenReturn(TRACK_NUMBER_METADATA_ONE);
		when(metadata_two.getTrackNumber()).thenReturn(TRACK_NUMBER_METADATA_TWO);
		metadatas.add(metadata_one);
		metadatas.add(metadata_two);
	}

	@Test
	public void shouldNotCompleteIfSingleTrack() throws Exception {
		List<Metadata> metadatas = new ArrayList<Metadata>();
		metadatas.add(metadata_one);

		defaultService.isCompletable(metadatas);

		verify(metadata_one, never()).setTotalTracks(TOTAL_TRACKS);
		verify(metadata_one, never()).setCdNumber(CD_NUMBER);
		verify(metadata_one, never()).setTotalCds(TOTAL_CD_NUMBER);
	}

	@Test
	public void shouldNotCompleteMetadataWhenNoNecesary() throws Exception {
		when(metadata_one.getTotalTracks()).thenReturn(TOTAL_TRACKS);
		when(metadata_two.getTotalTracks()).thenReturn(TOTAL_TRACKS);
		when(metadata_one.getCdNumber()).thenReturn(CD_NUMBER);
		when(metadata_two.getCdNumber()).thenReturn(CD_NUMBER);
		when(metadata_one.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
		when(metadata_two.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
		when(metadataService.isSameAlbum(metadatas)).thenReturn(true);

		assertFalse(defaultService.isCompletable(metadatas));
	}

	@Test
	public void shouldNotCompleteWhenNoTracknumber() throws Exception {
		when(metadata_one.getTotalTracks()).thenReturn(StringUtils.EMPTY);
		metadatas.add(metadata_one);
		defaultService.complete(metadatas);
		verify(metadata_one, never()).setCdNumber(CD_NUMBER);
		verify(metadata_one, never()).setTotalCds(TOTAL_CD_NUMBER);
	}

}
