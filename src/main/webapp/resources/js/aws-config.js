const albumBucketName = "hcvideo212";
const bucketRegion = "ap-south-1";
const IdentityPoolId = "ap-south-1:8ab0020b-b609-4377-899b-bd39c4aa120b";

AWS.config.update({
  credentials: new AWS.CognitoIdentityCredentials({
    IdentityPoolId: IdentityPoolId,
  }),
});

AWS.config.region = bucketRegion;

const s3 = new AWS.S3({
  apiVersion: "2006-03-01",
  params: {
    Bucket: albumBucketName,
  },
});
