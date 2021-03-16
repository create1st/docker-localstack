provider "aws" {
  region                      = "us-east-1"
  access_key                  = "dummy_access_key"
  secret_key                  = "dummy_secret_key"
  skip_credentials_validation = true
  skip_requesting_account_id  = true
  skip_metadata_api_check     = true
  s3_force_path_style         = true
  endpoints {
    s3 = "http://localhost:4572"
  }
}

terraform {
  backend "local" {}
}

resource "aws_s3_bucket" "docker-localstack-bucket" {
  bucket = "docker-localstack-bucket"
  acl    = "public-read"
}