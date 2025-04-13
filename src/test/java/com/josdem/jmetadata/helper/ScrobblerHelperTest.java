/*
   Copyright 2025 Jose Morales contact@josdem.io

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

package com.josdem.jmetadata.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.model.Model;
import com.josdem.jmetadata.model.User;
import de.umass.lastfm.Session;
import de.umass.lastfm.scrobble.ScrobbleResult;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.asmatron.messengine.ControlEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ScrobblerHelperTest {
  private static final String TRACK_NUMBER = "1";

  @InjectMocks private ScrobblerHelper helperScrobbler = new ScrobblerHelper();

  @Mock private Metadata metadata;
  @Mock private Map<Metadata, Long> metadataMap;
  @Mock private ControlEngine controlEngine;
  @Mock private User currentUser;
  @Mock private LastFMTrackHelper lastFMTrackHelper;
  @Mock private Session session;

  private ActionResult result;
  private String username = "josdem";
  private String password = "password";

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    when(currentUser.getUsername()).thenReturn(username);
    when(currentUser.getPassword()).thenReturn(password);
    when(controlEngine.get(Model.CURRENT_USER)).thenReturn(currentUser);
    helperScrobbler.setControlEngine(controlEngine);
  }

  @Test
  public void shouldNotAddAScrobblingifTrackSmallerThan240() throws Exception {
    setExpectations();
    when(metadata.getArtist()).thenReturn("Above & Beyond");
    when(metadata.getTitle()).thenReturn("Anjunabeach");

    result = helperScrobbler.send(metadata);

    notSendToScrobblingMapAssertion();
  }

  @Test
  public void shouldNotAddAScrobblingIfNoArtist() throws Exception {
    setExpectations();
    when(metadata.getArtist()).thenReturn(StringUtils.EMPTY);
    when(metadata.getTitle()).thenReturn("Anjunabeach");

    result = helperScrobbler.send(metadata);

    notSendToScrobblingMapAssertion();
  }

  private void notSendToScrobblingMapAssertion() {
    verify(metadataMap, never()).size();
    verify(metadataMap, never()).put(isA(Metadata.class), isA(Long.class));
    assertEquals(ActionResult.NOT_RECORDED, result);
  }

  @Test
  public void shouldNotAddAScrobblingIfNoTitle() throws Exception {
    setExpectations();
    when(metadata.getArtist()).thenReturn("Above & Beyond");
    when(metadata.getTitle()).thenReturn(StringUtils.EMPTY);

    result = helperScrobbler.send(metadata);
    notSendToScrobblingMapAssertion();
  }

  @Test
  public void shouldFailWhenSubmitScrobbler() throws Exception {
    ScrobbleResult result = mock(ScrobbleResult.class);
    when(metadataMap.get(metadata)).thenReturn(100L);
    setExpectations();
    setMetadataTrackExpectations();
    when(result.isSuccessful()).thenReturn(false);
    when(lastFMTrackHelper.scrobble(
            metadata.getArtist(),
            metadata.getTitle(),
            metadataMap.get(metadata).intValue(),
            currentUser.getSession()))
        .thenReturn(result);

    assertEquals(ActionResult.SESSION_LESS, helperScrobbler.send(metadata));
  }

  private void setMetadataTrackExpectations() {
    when(metadata.getLength()).thenReturn(300);
    when(metadata.getArtist()).thenReturn("Above & Beyond");
    when(metadata.getTitle()).thenReturn("Anjunabeach");
  }

  @Test
  public void shouldSendAnScrobbler() throws Exception {
    ScrobbleResult result = mock(ScrobbleResult.class);
    when(metadataMap.get(metadata)).thenReturn(100L);
    when(currentUser.getSession()).thenReturn(session);
    setExpectations();
    setMetadataTrackExpectations();
    when(result.isSuccessful()).thenReturn(true);
    when(lastFMTrackHelper.scrobble(
            metadata.getArtist(),
            metadata.getTitle(),
            metadataMap.get(metadata).intValue(),
            currentUser.getSession()))
        .thenReturn(result);

    assertEquals(ActionResult.SENT, helperScrobbler.send(metadata));
  }

  private void setExpectations() {
    when(metadata.getAlbum()).thenReturn(StringUtils.EMPTY);
    when(metadata.getLength()).thenReturn(1);
    when(metadata.getTrackNumber()).thenReturn(TRACK_NUMBER);
  }

  @Test
  public void shouldReturnIfNoLogin() throws Exception {
    setMetadataTrackExpectations();
    when(currentUser.getUsername()).thenReturn(StringUtils.EMPTY);

    assertEquals(ActionResult.NOT_LOGGED, helperScrobbler.send(metadata));
  }
}
