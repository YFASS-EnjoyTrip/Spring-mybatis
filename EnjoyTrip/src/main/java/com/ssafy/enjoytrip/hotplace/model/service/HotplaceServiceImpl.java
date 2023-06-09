package com.ssafy.enjoytrip.hotplace.model.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.global.dto.FileDto;
import com.ssafy.enjoytrip.global.mapper.FileMapper;
import com.ssafy.enjoytrip.global.mapper.LikeMapper;
import com.ssafy.enjoytrip.hotplace.dto.HotPlaceDto;
import com.ssafy.enjoytrip.hotplace.model.mapper.HotplaceMapper;
import com.ssafy.enjoytrip.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class HotplaceServiceImpl implements HotplaceService {

	private final HotplaceMapper hotplaceMapper;
	private final FileMapper fileMapper;
	private final LikeMapper likeMapper;
	private final String UPLOAD_PATH = "/upload/hotplace";
	@Autowired
	private ServletContext servletContext;

	@Override
	public ResponseEntity<ResponseDto> list() {
		String msg;
		try {
			List<HotPlaceDto> list = hotplaceMapper.selectAllHotplace();
			log.info("service : list = {}", list);
			msg = "핫플레이스 목록 조회 정상적으로 수행";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, list));
		} catch (Exception e) {
			msg = "서버에 문제가 발생했습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		}
	}

	@Override
	public ResponseEntity<ResponseDto> detail(String id) {
		String msg;
		try {
			hotplaceMapper.updateViewCount(id);
			HotPlaceDto h = hotplaceMapper.selectDetail(id);
			log.info("service : detail = {}", h);
			msg = "핫플레이스 상세 조회 정상적으로 수행";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, h));
		} catch (Exception e) {
			msg = "서버에 문제가 발생했습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		}
	}

	@Override
	public ResponseEntity<ResponseDto> write(HotPlaceDto hotplace, MultipartFile[] files) {
		String msg;
		log.info("MultipartFile.isEmpty : {}", files[0].isEmpty());
		try {
			log.info("service : write = {}", hotplace);
			hotplaceMapper.insertHotplace(hotplace);
			String hotplacePK = hotplace.getId();
			if (!files[0].isEmpty()) {
				String realPath = servletContext.getRealPath(UPLOAD_PATH);
				String today = new SimpleDateFormat("yyMMdd").format(new Date());
				String saveFolder = realPath + File.separator + today;
				log.info("저장 폴더 : {}", saveFolder);
				File folder = new File(saveFolder);
				if (!folder.exists())
					folder.mkdirs();
				List<FileDto> fileInfos = new ArrayList<>();
				for (MultipartFile mfile : files) {
					FileDto fileDto = new FileDto();
					String originalFileName = mfile.getOriginalFilename();
					if (!originalFileName.isEmpty()) {
						String type = originalFileName.substring(originalFileName.lastIndexOf('.'));
						String saveFileName = UUID.randomUUID().toString() + type;
						String size = "" + mfile.getSize();
						fileDto.setSaveFolder(today);
						fileDto.setOriginalFile(originalFileName);
						fileDto.setSaveFile(saveFileName);
						fileDto.setType(type);
						fileDto.setSize(size);
						log.info("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}, 확장자 : {}", mfile.getOriginalFilename(), saveFileName,
								type);
						mfile.transferTo(new File(folder, saveFileName));
					}
					fileInfos.add(fileDto);
					log.info("fileDto : {}", fileDto);
					fileMapper.insertFileInfo(fileDto);
					Map<String, Object> map = new HashMap<>();
					map.put("hotplaceId",hotplacePK);
					map.put("fileId",fileDto.getFileId());
					log.info("HotplaceFileMap : {}", map);
					fileMapper.insertHotplaceFileMap(map);
				}
				// 입력된 파일 중 첫번째값 썸네일로 사용하기 위해 select해서 가져오기
				log.info("selectFileIdByHotplaceId : {}", hotplacePK);
				String fileId = fileMapper.selectFileIdByHotplaceId(hotplacePK);
				// 가져온 fileId를 hotplace테이블의 사진 컬럼에 넣기
				Map<String, String> img = new HashMap<String, String>();
				img.put("fileId",fileId);
				img.put("hotplaceId",hotplacePK);
				log.info("updateContentImg : {}", img);
				hotplaceMapper.updateContentImg(img);
			}

			
			msg = "핫플레이스 게시글 작성 정상적으로 수행";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, null));
		} catch (Exception e) {
			e.printStackTrace();
			msg = "서버에 문제가 발생했습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		}
	}

	@Override
	public ResponseEntity<ResponseDto> edit(HotPlaceDto hotplace) {
		String msg;
		try {
			log.info("service : edit = {}", hotplace);
			hotplaceMapper.updateHotplace(hotplace);
			msg = "핫플레이스 게시글 수정 정상적으로 수행";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, null));
		} catch (Exception e) {
			msg = "서버에 문제가 발생했습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		}
	}

	@Override
	public ResponseEntity<ResponseDto> delete(String id) {
		String msg;
		try {
			log.info("service : delete = {}", id);
			hotplaceMapper.deleteHotplace(id);
			msg = "핫플레이스 게시글 삭제 정상적으로 수행";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, null));
		} catch (Exception e) {
			msg = "서버에 문제가 발생했습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		}
	}

	@Override
	public ResponseEntity<ResponseDto> like(Map<String, String> map) {
		String msg;
		map.put("type", "H");
		try {
			log.info("service : delete = {}", map);
			// 1. select 수행 -> 매핑 테이블에 현재 있는지 확인
			int isExist = likeMapper.selectLike(map);
			log.info("service : isExist = {}", isExist);

			// 2-1. select 결과 != null => delete
			if (isExist > 0) {
				log.info("좋아요 한 이력 있음 -> 좋아요 삭제");
				likeMapper.deleteLike(map);
				map.put("check", "delete");
			}

			// 2-2. select 결과 == null => insert
			else {
				log.info("좋아요 한 이력 없음 -> 좋아요 추가");
				likeMapper.insertLike(map);
				map.put("check", "insert");
			}

			// 3. hotplace 테이블 like_count update
			log.info("핫플레이스 좋아요 수 업데이트");
			hotplaceMapper.updateHotplaceLike(map);

			msg = "좋아요 수 변경 정상적으로 수행";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.value(), msg, null));
		} catch (Exception e) {
			e.printStackTrace();
			msg = "서버에 문제가 발생했습니다.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null));
		}
	}
}
