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

package org.jas.collaborator;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jas.model.Metadata;

public class MetadataCollaborator {

	private List<Metadata> metadatas;

	public void setMetadatas(List<Metadata> metadatas) {
		this.metadatas = metadatas;
	}

	public String getArtist() {
		boolean isTheSame = true;
		for (int i=1; i< metadatas.size(); i++) {
			if (metadatas.get(i).getArtist() == null && metadatas.get(i-1).getArtist() == null){
				return StringUtils.EMPTY;
			}
			if (!metadatas.get(i).getArtist().equals(metadatas.get(i-1).getArtist())){
				isTheSame = false;
			}
		}
		return isTheSame ? metadatas.get(0).getArtist() : StringUtils.EMPTY;
	}

	public String getAlbum() {
		boolean isTheSame = true;
		for (int i=1; i< metadatas.size(); i++) {
			if (metadatas.get(i).getAlbum() == null && metadatas.get(i-1).getAlbum() == null){
				return StringUtils.EMPTY;
			}
			if (!metadatas.get(i).getAlbum().equals(metadatas.get(i-1).getAlbum())){
				isTheSame = false;
			}
		}
		return isTheSame ? metadatas.get(0).getAlbum() : StringUtils.EMPTY;
	}

	public String getGenre() {
		boolean isTheSame = true;
		for (int i=1; i< metadatas.size(); i++) {
			if (metadatas.get(i).getGenre() == null && metadatas.get(i-1).getGenre() == null){
				return StringUtils.EMPTY;
			}
			if (!metadatas.get(i).getGenre().equals(metadatas.get(i-1).getGenre())){
				isTheSame = false;
			}
		}
		return isTheSame ? metadatas.get(0).getGenre() : StringUtils.EMPTY;
	}

	public String getYear() {
		boolean isTheSame = true;
		for (int i=1; i< metadatas.size(); i++) {
			if (metadatas.get(i).getYear() == null && metadatas.get(i-1).getYear() == null){
				return StringUtils.EMPTY;
			}
			if (!metadatas.get(i).getYear().equals(metadatas.get(i-1).getYear())){
				isTheSame = false;
			}
		}
		return isTheSame ? metadatas.get(0).getYear() : StringUtils.EMPTY;
	}

	public String getTotalTracks() {
		boolean isTheSame = true;
		for (int i=1; i< metadatas.size(); i++) {
			if (metadatas.get(i).getTotalTracks() == null && metadatas.get(i-1).getTotalTracks() == null){
				return StringUtils.EMPTY;
			}
			if (!metadatas.get(i).getTotalTracks().equals(metadatas.get(i-1).getTotalTracks())){
				isTheSame = false;
			}
		}
		return isTheSame ? metadatas.get(0).getTotalTracks() : StringUtils.EMPTY;
	}

	public String getTotalCds() {
		boolean isTheSame = true;
		for (int i=1; i< metadatas.size(); i++) {
			if (metadatas.get(i).getTotalCds() == null && metadatas.get(i-1).getTotalCds() == null){
				return StringUtils.EMPTY;
			}
			if (!metadatas.get(i).getTotalCds().equals(metadatas.get(i-1).getTotalCds())){
				isTheSame = false;
			}
		}
		return isTheSame ? metadatas.get(0).getTotalCds() : StringUtils.EMPTY;
	}

	public String getCdNumber() {
		boolean isTheSame = true;
		for (int i=1; i< metadatas.size(); i++) {
			if (metadatas.get(i).getCdNumber() == null && metadatas.get(i-1).getCdNumber() == null){
				return StringUtils.EMPTY;
			}
			if (!metadatas.get(i).getCdNumber().equals(metadatas.get(i-1).getCdNumber())){
				isTheSame = false;
			}
		}
		return isTheSame ? metadatas.get(0).getCdNumber() : StringUtils.EMPTY;
	}

}
