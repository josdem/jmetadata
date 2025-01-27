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

package com.josdem.jmetadata.model;

import org.apache.commons.lang3.StringUtils;

public enum GenreTypes {
  BLUES(0, "Blues"),
  CLASSIC_ROCK(1, "Classic Rock"),
  COUNTRY(2, "Country"),
  DANCE(3, "Dance"),
  DISCO(4, "Disco"),
  FUNK(5, "Funk"),
  GRUNGE(6, "Grunge"),
  HIP_HOP(7, "Hip-Hop"),
  JAZZ(8, "Jazz"),
  METAL(9, "Metal"),
  NEW_AGE(10, "New Age"),
  OLDIES(11, "Oldies"),
  OTHER(12, "Other"),
  POP(13, "Pop"),
  R_and_B(14, "R&B"),
  RAP(15, "Rap"),
  REGGAE(16, "Reggae"),
  ROCK(17, "Rock"),
  TECHNO(18, "Techno"),
  INDUSTRIAL(19, "Industrial"),
  ALTERNATIVE(20, "Alternative"),
  SKA(21, "Ska"),
  DEATH_METAL(22, "Death Metal"),
  PRANKS(23, "Pranks"),
  SOUNDTRACK(24, "Soundtrack"),
  EURO_TECHNO(25, "Euro-Techno"),
  AMBIENT(26, "Ambient"),
  TRIP_HOP(27, "Trip-Hop"),
  VOCAL(28, "Vocal"),
  JAZZ_FUNK(29, "Jazz+Funk"),
  FUSION(30, "Fusion"),
  TRANCE(31, "Trance"),
  CLASSICAL(32, "Classical"),
  INSTRUMENTAL(33, "Instrumental"),
  ACID(34, "Acid"),
  HOUSE(35, "House"),
  GAME(36, "Game"),
  SOUND_CLIP(37, "Sound Clip"),
  GOSPEL(38, "Gospel"),
  NOISE(39, "Noise"),
  ALTERNATIVE_ROCK(40, "Alternative Rock"),
  BASS(41, "Bass"),
  SOUL(42, "Soul"),
  PUNK(43, "Punk"),
  SPACE(44, "Space"),
  MEDITATIVE(45, "Meditative"),
  INSTRUMENTAL_POP(46, "Instrumental Pop"),
  INSTRUMENTAL_ROCK(47, "Instrumental Rock"),
  ETHNIC(48, "Ethnic"),
  GOTHIC(49, "Gothic"),
  DARKWAVE(50, "Darkwave"),
  TECHN0_INDUSTRIAL(51, "Techno-Industrial"),
  ELECTRONIC(52, "Electronic"),
  POP_FOLK(53, "Pop-Folk"),
  EURODANCE(54, "Eurodance"),
  DREAM(55, "Dream"),
  SOUTHERN_ROCK(56, "Southern Rock"),
  COMEDY(57, "Comedy"),
  CULT(58, "Cult"),
  GANGSTA(59, "Gangsta"),
  TOP_40(60, "Top 40"),
  CHRISTIAN_RAP(61, "Christian Rap"),
  POP_FUNK(62, "Pop/Funk"),
  JUNGLE(63, "Jungle"),
  NATIVE_US(64, "Native US"),
  CABARET(65, "Cabaret"),
  NEW_WAVE(66, "New Wave"),
  PSYCHADELIC(67, "Psychadelic"),
  RAVE(68, "Rave"),
  SHOWTUNES(69, "Showtunes"),
  TRAILER(70, "Trailer"),
  LO_FI(71, "Lo-Fi"),
  TRIBAL(72, "Tribal"),
  ACID_PUNK(73, "Acid Punk"),
  ACID_JAZZ(74, "Acid Jazz"),
  POLKA(75, "Polka"),
  RETRO(76, "Retro"),
  MUSICAL(77, "Musical"),
  ROCK_ROLL(78, "Rock & Roll"),
  HARD_ROCK(79, "Hard Rock"),
  FOLK(80, "Folk"),
  FOLK_ROCK(81, "Folk-Rock"),
  NATIONAL_FOLK(82, "National Folk"),
  SWING(83, "Swing"),
  FAST_FUSION(84, "Fast Fusion"),
  BEBOB(85, "Bebob"),
  LATIN(86, "Latin"),
  REVIVAL(87, "Revival"),
  CELTIC(88, "Celtic"),
  BLUEGRASS(89, "Bluegrass"),
  AVANTGARDE(90, "Avantgarde"),
  GOTHIC_ROCK(91, "Gothic Rock"),
  PROGRESSIVE_ROCK(92, "Progressive Rock"),
  PSYCHEDELIC_ROCK(93, "Psychedelic Rock"),
  SYMPHONIC_ROCK(94, "Symphonic Rock"),
  SLOW_ROCK(95, "Slow Rock"),
  BIG_BAND(96, "Big Band"),
  CHORUS(97, "Chorus"),
  EASY_LISTENING(98, "Easy Listening"),
  ACOUSTIC(99, "Acoustic"),
  HUMOUR(100, "Humour"),
  SPEECH(101, "Speech"),
  CHANSON(102, "Chanson"),
  OPERA(103, "Opera"),
  CHAMBER_MUSIC(104, "Chamber Music"),
  SONATA(105, "Sonata"),
  SYMPHONY(106, "Symphony"),
  BOOTY_BASS(107, "Booty Bass"),
  PRIMUS(108, "Primus"),
  PORN_GROOVE(109, "Porn Groove"),
  SALTIRE(110, "Satire"),
  SLOW_JAM(111, "Slow Jam"),
  CLUB(112, "Club"),
  TANGO(113, "Tango"),
  SAMBA(114, "Samba"),
  FOLKLORE(115, "Folklore"),
  BALLAD(116, "Ballad"),
  POWER_BALLAD(117, "Power Ballad"),
  RHYTHMIC_SOUL(118, "Rhythmic Soul"),
  FREESTYLE(119, "Freestyle"),
  DUET(120, "Duet"),
  PUNK_ROCK(121, "Punk Rock"),
  DRUM_SOLO(122, "Drum Solo"),
  ACAPELLA(123, "Acapella"),
  EURO_HOUSE(124, "Euro-House"),
  DANCE_HALL(125, "Dance Hall"),
  GOA(126, "Goa"),
  DRUM_BASS(127, "Drum & Bass"),
  CLUB_HOUSE(128, "Club ,House"),
  HARDCORE(129, "Hardcore"),
  TERROR(130, "Terror"),
  INDIE(131, "Indie"),
  BRITPOP(132, "BritPop"),
  NEGERPUNK(133, "Negerpunk"),
  POLSK_PUNK(134, "Polsk Punk"),
  BEAT(135, "Beat"),
  CHRISTIAN_GANGSTA_RAP(136, "Christian Gangsta Rap"),
  HEAVY_METAL(137, "Heavy Metal"),
  BLACK_METAL(138, "Black Metal"),
  CROSSOVER(139, "Crossover"),
  CONTEMPORARY_CHRISTIAN(140, "Contemporary Christian"),
  CHRISTIAN_ROCK(141, "Christian Rock"),
  MERENGUE(142, "Merengue"),
  SALSA(143, "Salsa"),
  THRASH_METAL(144, "Thrash Metal"),
  ANIME(145, "Anime"),
  JPOP(146, "JPop"),
  SYNTHPOP(147, "Synthpop"),
  UNKNOWN(148, "Unknown"),
  LOUNGE(149, "Lounge"),
  VOCAL_TRANCE(150, "Vocal Trance"),
  DOWNTEMPO(151, "Downtempo"),
  MINIMAL(152, "Minimal");

  private final String name;
  private final int code;

  private GenreTypes(int code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getCode() {
    return code;
  }

  public static String getGenreByCode(int code) {
    for (GenreTypes item : GenreTypes.values()) {
      if (item.getCode() == code) {
        return item.getName();
      }
    }
    return StringUtils.EMPTY;
  }

  public static GenreTypes getGenreByName(String name) {
    for (GenreTypes item : GenreTypes.values()) {
      if (item.getName().toLowerCase().equals(name.toLowerCase())) {
        return item;
      }
    }
    return UNKNOWN;
  }
}
