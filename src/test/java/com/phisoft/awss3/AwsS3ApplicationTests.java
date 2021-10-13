package com.phisoft.awss3;

import com.phisoft.awss3.profile.PetProfile;
import com.phisoft.awss3.service.PetProfileService;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AwsS3ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    public PetProfileService petProfileService;

    private static UUID petProfileId;

    @BeforeAll
    private static void init() {
        petProfileId=UUID.randomUUID();

    }

    @Test
    void testGetProfileEndPoint() {
        BDDMockito.given(this.petProfileService.getPetProfiles()).willReturn(Arrays.asList(new PetProfile(UUID.randomUUID(),"Brown","s3-bucket-one"),new PetProfile(UUID.randomUUID(),"Little","s3-bucket-two"),new PetProfile(UUID.randomUUID(),"ebube-agu","s3-bucket-three")));
        Assertions.assertEquals(3, this.petProfileService.getPetProfiles().size());
    }

    @Test
     void testUploadPictureEndPoint() throws Exception {
        final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.png");
        final MockMultipartFile image = new MockMultipartFile("file", "test.png", "image/png", inputStream);
        doNothing().when(petProfileService).uploadImage(petProfileId, image);
         mockMvc.perform(fileUpload("/"+petProfileId.toString()+"/image/upload").file(image))
                .andExpect(status().isOk())
                .andReturn();
        verify(petProfileService).uploadImage(petProfileId, image);
    }

    @Test
     void testDownloadFile() throws Exception {
        Mockito.when(petProfileService.downloadImage(petProfileId)).thenReturn(new byte[255]);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/"+petProfileId.toString()+"/image/download").contentType(MediaType.APPLICATION_OCTET_STREAM)).andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals(2, result.getResponse().getContentAsByteArray().length);
    }

}
