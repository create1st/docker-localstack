terraform {
  backend "local" {}
}

provider "aws" {
  alias                       = "local"
  access_key                  = "mock_access_key"
  region                      = "us-east-1"
  s3_force_path_style         = true
  secret_key                  = "mock_secret_key"
  skip_credentials_validation = true
  skip_metadata_api_check     = true
  skip_requesting_account_id  = true

  endpoints {
    s3             = "http://s3:4572"
    secretsmanager = "http://secretsmanager:4584"
    sns            = "http://sns:4575"
    sqs            = "http://sqs:4576"
  }
}

resource "aws_s3_bucket" "docker_localstack_bucket" {
  bucket   = "docker-localstack-bucket"
  provider = aws.local
  acl      = "private"
}